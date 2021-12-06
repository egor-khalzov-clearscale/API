package resources;

import com.opencsv.CSVWriter;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.collections.ListUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import utilities.ResponseTimeFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class ResponseErrorLogger {

    static ArrayList<TestData> ar = new ArrayList<>();
    static CSVWriter outputCsv;

    static {
        RestAssured.filters(new ResponseTimeFilter());
    }

    public static void setResponse(String s, String testName, ResponseTimeFilter.HttpRequestAttachment requestSpecification, Response response) {
        String[] classPath = s.split("\\.");
        ar.add(new TestData(classPath[0], classPath[1], testName, requestSpecification, response));
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        var collect = ar.stream()
                .map(d -> d.setTestThrowable(result.getThrowable()))
                .collect(Collectors.toMap(TestData::getFullName, List::of, (BinaryOperator<List<TestData>>) ListUtils::union));
        if (result.getStatus() == ITestResult.FAILURE) {
            collect.forEach(this::saveErrorData);
        }
        collect.forEach((k, v) -> buildCSV(v, result.getStatus()));
        ar.clear();
    }

    @AfterTest
    public void after() throws IOException {
        outputCsv.close();
    }

    private File iterVersionIfFileExist(String path, int version, String ext) {
        var file = new File(path + (version == 0 ? "" : "_" + version) + (ext != null ? "." + ext : ""));
        return file.exists() ? iterVersionIfFileExist(path, ++version, ext) : file;
    }

    private void saveErrorData(String s, List<TestData> testData) {
        testData.forEach(this::parseErrorDataFromResponse);
        TestData lastCall = testData.get(testData.size() - 1);
        String basePath = "./result/" + lastCall.error + "/" + lastCall.getFullName();
        var time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        new File(basePath + "/_" + time).mkdirs();
        testData.forEach(data -> saveErrorData(data, basePath));
    }

    public void parseErrorDataFromResponse(TestData data) {
        var testThrowable = data.getTestThrowable();
        if (data.getTestThrowable() instanceof AssertionError &&
                !testThrowable.getMessage().contains("status code")) {
            data.error = "AssertionError/" + testThrowable.getMessage();
        } else if (data.response.getHeaders() == null) {
            data.error = "AssertionError/" + testThrowable.getMessage();
        } else if (data.response.getHeaders().getValue("Content-Type").contains("json")) {
            data.error = getErrorReasonJson(ReusableMethods.rawToJson(data.response));
        } else data.error = getErrorReasonXml(ReusableMethods.rawToXML(data.response));
        System.out.println("Test " + data.getFullName() + " has an error:\n" + data.error);
    }
    private String getErrorReasonJson(JsonPath json) {
        var errorMsg = json.getString("Message");
        if (errorMsg == null) return "unsorted";
        if (errorMsg.contains("<faultcode>")) {
            errorMsg = errorMsg.replace("Internal server error - ", "");
            return getErrorReasonXml(new XmlPath(errorMsg));
        }
        return !errorMsg.isEmpty() ? errorMsg : "unsorted";
    }

    public void saveErrorData(TestData e, String path) {
        File file = new File(path);
        file.mkdirs();
        var response = iterVersionIfFileExist(path + "/response", 0, "xml");
        var request = iterVersionIfFileExist(path + "/request", 0, "xml");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(request));
            writer.write(e.requestSpecification.getCurl() != null ? e.requestSpecification.getCurl() : "empty body");
            writer.close();
            writer = new BufferedWriter(new FileWriter(response));
            writer.write(e.response.getBody().asPrettyString() != null ? e.response.getBody().asPrettyString() : "empty body");
            writer.close();
        } catch (IOException ee) {
            System.out.println("an error occurs ");
        }
    }

    private void initFileWriter() {
        try {
            var time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
            File file = new File("./result_" + time + ".csv");
            var fileWriter = new FileWriter(file, true);
            outputCsv = new CSVWriter(fileWriter);
            String[] headers = {"Status", "Package", "Class", "Test", "Error"};
            outputCsv.writeNext(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildCSV(List<TestData> dataList, int testStatus) {
        if (outputCsv == null) initFileWriter();
        var data = dataList.get(dataList.size() - 1);
        String[] dataToCsv = {testStatus == 2 ? "FAILURE" : "SUCCESS", data.packageName, data.className, data.testName, data.error};
        outputCsv.writeNext(dataToCsv);
    }

    private String getErrorReasonXml(XmlPath js) {
        String errorReason;
        try {
            errorReason = js.getString("Fault.detail.InternalServerErrorFaultDto.Details");
            String topMessage = js.getString("Fault.detail.InternalServerErrorFaultDto.Message");
            errorReason = reqMsg(errorReason, topMessage);
            if (errorReason.isEmpty())
                errorReason = topMessage;
            if (errorReason.isEmpty()) errorReason = js.getString("Fault.faultstring").split("\n")[0];
            if (errorReason.isEmpty()) errorReason = "unsorted";
        } catch (Exception e) {
            errorReason = "unsorted";
        }
        return errorReason.split("\n")[0].trim();
    }

    private String reqMsg(String msg, String topMsg) {
        try {
            msg = msg.substring(msg.indexOf("Message:") + 8);
            var errorReason = msg.substring(0, msg.indexOf("Source:"));
            if (errorReason.trim().equals(topMsg.trim())) {
                return reqMsg(msg, topMsg);
            } else return errorReason;
        } catch (Exception e) {
            return topMsg;
        }
    }

    public static class TestData {
        public Throwable testThrowable;
        public ResponseTimeFilter.HttpRequestAttachment requestSpecification;
        public Response response;
        public String packageName;
        public String className;
        public String testName;
        public String error = "";

        public TestData(String packageName, String className, String testName, ResponseTimeFilter.HttpRequestAttachment requestSpecification, Response response) {
            this.requestSpecification = requestSpecification;
            this.response = response;
            this.packageName = packageName;
            this.className = className;
            this.testName = testName;
        }

        public TestData(ResponseTimeFilter.HttpRequestAttachment requestSpecification, Response response) {
            this.requestSpecification = requestSpecification;
            this.response = response;

        }

        public Throwable getTestThrowable() {
            return testThrowable;
        }

        public TestData setTestThrowable(Throwable testThrowable) {
            this.testThrowable = testThrowable;
            return this;
        }

        public String getFullName() {
            return this.packageName + "." + this.className + "." + this.testName;
        }

        public ResponseTimeFilter.HttpRequestAttachment getRequestSpecification() {
            return requestSpecification;
        }

        public Response getResponse() {
            return response;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getClassName() {
            return className;
        }

        public String getTestName() {
            return testName;
        }

        public String getError() {
            return error;
        }
    }

}
