package utilities;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.testng.annotations.Test;
import resources.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseTimeFilter implements Filter {

    private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));
        return result;
    }

    @Override
    public Response filter(final FilterableRequestSpecification requestSpec,
                           final FilterableResponseSpecification responseSpec,
                           final FilterContext filterContext) {
        final Prettifier prettifier = new Prettifier();
        final String url = requestSpec.getURI();
        final HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder
                .create("Request", url)
                .setMethod(requestSpec.getMethod())
                .setHeaders(toMapConverter(requestSpec.getHeaders()))
                .setCookies(toMapConverter(requestSpec.getCookies()));

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        final HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();

        final Response response = filterContext.next(requestSpec, responseSpec);

        Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(this::isTestPackege).findFirst()
//                .filter(this::filterTestOnly)
                .ifPresent(trace -> base.setResponse(trace.getClassName().replace("markerPackege.", ""), trace.getMethodName(), requestAttachment, response));
        return response;
    }

    private boolean isTestPackege(StackTraceElement s) {
        return s.getClassName().startsWith("archieve")
                || s.getClassName().startsWith("functionalTests")
                || s.getClassName().startsWith("unitTests");
    }

    private boolean filterTestOnly(StackTraceElement trace) {
        try {
            return Class.forName(trace.getClassName()).getMethod(trace.getMethodName()).getAnnotation(Test.class) != null;
        } catch (NoSuchMethodException | ClassNotFoundException ignored) {
        }
        return false;
    }

    public static class HttpRequestAttachment {

        private final String name;

        private final String url;

        private final String method;

        private final String body;

        private final String curl;

        private final Map<String, String> headers;

        private final Map<String, String> cookies;

        public HttpRequestAttachment(final String name, final String url, final String method,
                                     final String body, final String curl, final Map<String, String> headers,
                                     final Map<String, String> cookies) {
            this.name = name;
            this.url = url;
            this.method = method;
            this.body = body;
            this.curl = curl;
            this.headers = headers;
            this.cookies = cookies;
        }

        public String getUrl() {
            return url;
        }

        public String getMethod() {
            return method;
        }

        public String getBody() {
            return body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Map<String, String> getCookies() {
            return cookies;
        }

        public String getCurl() {
            return curl;
        }

        public String getName() {
            return name;
        }

        public static final class Builder {

            private final String name;

            private final String url;
            private final Map<String, String> headers = new HashMap<>();
            private final Map<String, String> cookies = new HashMap<>();
            private String method;
            private String body;

            private Builder(final String name, final String url) {
                Objects.requireNonNull(name, "Name must not be null value");
                Objects.requireNonNull(url, "Url must not be null value");
                this.name = name;
                this.url = url;
            }

            public static Builder create(final String attachmentName, final String url) {
                return new Builder(attachmentName, url);
            }

            private static void appendHeader(final StringBuilder builder, final String key, final String value) {
                builder.append(" -H '")
                        .append(key)
                        .append(": ")
                        .append(value)
                        .append('\'');
            }

            private static void appendCookie(final StringBuilder builder, final String key, final String value) {
                builder.append(" -b '")
                        .append(key)
                        .append('=')
                        .append(value)
                        .append('\'');
            }

            public Builder setMethod(final String method) {
                Objects.requireNonNull(method, "Method must not be null value");
                this.method = method;
                return this;
            }

            public Builder setHeader(final String name, final String value) {
                Objects.requireNonNull(name, "Header name must not be null value");
                Objects.requireNonNull(value, "Header value must not be null value");
                this.headers.put(name, value);
                return this;
            }

            public Builder setHeaders(final Map<String, String> headers) {
                Objects.requireNonNull(headers, "Headers must not be null value");
                this.headers.putAll(headers);
                return this;
            }

            public Builder setCookie(final String name, final String value) {
                Objects.requireNonNull(name, "Cookie name must not be null value");
                Objects.requireNonNull(value, "Cookie value must not be null value");
                this.cookies.put(name, value);
                return this;
            }

            public Builder setCookies(final Map<String, String> cookies) {
                Objects.requireNonNull(cookies, "Cookies must not be null value");
                this.cookies.putAll(cookies);
                return this;
            }

            public Builder setBody(final String body) {
                Objects.requireNonNull(body, "Body should not be null value");
                this.body = body;
                return this;
            }

            public HttpRequestAttachment build() {
                return new HttpRequestAttachment(name, url, method, body, getCurl(), headers, cookies);
            }

            private String getCurl() {
                final StringBuilder builder = new StringBuilder("curl -v");
                if (Objects.nonNull(method)) {
                    builder.append(" -X ").append(method);
                }
                builder.append(" '").append(url).append('\'');
                headers.forEach((key, value) -> appendHeader(builder, key, value));
                cookies.forEach((key, value) -> appendCookie(builder, key, value));

                if (Objects.nonNull(body)) {
                    builder.append(" -d '").append(body).append('\'');
                }
                return builder.toString();
            }
        }


    }
}
