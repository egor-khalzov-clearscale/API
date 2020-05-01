package payloads;

import resources.base;

public class FinancialPL extends base {
	
	public static String takePaymentWithNewCreditCard_AllParameters
			(String cardNumber,
			String nameOnCard,
			String expirationDate,
			String securityCode,
			String addressLine1,
			String addressLine2,
			String city,
			String stateProvince,
			String postalCode,
			String country,
			String customerId,
			String employeeBarcodeId,
			String clubId,
			Double amount,
			String effectiveDate,
			String paymentDescription,
			String paymentCategory,
			int invoiceId) {
		
		String payload = "{\r\n" + 
				"  \"CardNumber\": "+cardNumber+"," + 
				"  \"NameOnCard\": \""+nameOnCard+"\"," + 
				"  \"ExpirationDate\": \""+expirationDate+"\","+ 
				"  \"SecurityCode\": "+securityCode+"," +
				"  \"AddressLine1\": \""+addressLine1+"\"," +
				"  \"AddressLine2\": \""+addressLine2+"\"," +
				"  \"City\": \""+city+"\"," +
				"  \"StateProvince\": \""+stateProvince+"\"," +
				"  \"PostalCode\": "+postalCode+"," +
				"  \"Country\": "+country+"," +
				"  \"CustomerId\": "+customerId+"," +
				"  \"EmployeeBarcodeId\": \""+employeeBarcodeId+"\"," +
				"  \"ClubId\": "+clubId+"," +
				"  \"Amount\": "+amount+"," +
				"  \"EffectiveDate\": \""+effectiveDate+"\"," +
				"  \"PaymentDescription\": \""+paymentDescription+"\"," +
				"  \"PaymentCategory\": \""+paymentCategory+"\"," +
				"  \"InvoiceId\": "+invoiceId+"," +
				"}";
		
		return payload;
	}
	
	public static String takePaymentWithNewCreditCard_RequiredParametersOnly
			(String cardNumber,
			String nameOnCard,
			String expirationDate,
			String securityCode,
			String addressLine1,
			String city,
			String stateProvince,
			String postalCode,
			String customerId,
			String employeeBarcodeId,
			String clubId,
			Double amount,
			String effectiveDate,
			String paymentDescription,
			String paymentCategory) {
	
		String payload = "{\r\n" + 
				"  \"CardNumber\": \""+cardNumber+"\"," + 
				"  \"NameOnCard\": \""+nameOnCard+"\"," + 
				"  \"ExpirationDate\": \""+expirationDate+"\","+ 
				"  \"SecurityCode\": "+securityCode+"," +
				"  \"AddressLine1\": \""+addressLine1+"\"," +
				"  \"City\": \""+city+"\"," +
				"  \"StateProvince\": \""+stateProvince+"\"," +
				"  \"PostalCode\": "+postalCode+"," +
				"  \"CustomerId\": "+customerId+"," +
				"  \"EmployeeBarcodeId\": \""+employeeBarcodeId+"\"," +
				"  \"ClubId\": "+clubId+"," +
				"  \"Amount\": "+amount+"," +
				"  \"EffectiveDate\": \""+effectiveDate+"\"," +
				"  \"PaymentDescription\": \""+paymentDescription+"\"," +
				"  \"PaymentCategory\": \""+paymentCategory+"\"," +
				"}";
	
			return payload;
		}	
	
	public static String takePaymentWithCardOnFileForMemberWithInvoice
		(String accountId,
		String customerId,
		String employeeBarcodeId,
		String clubId,
		String amount,
		String effectiveDate,
		String paymentDescription,
		String paymentCategory,
		int invoiceId
		) {
		
		
		String payload = "{\r\n" + 
				"  \"AccountId\": \""+accountId+"\"," + 
				"  \"CustomerId\": \""+customerId+"\"," +
				"  \"EmployeeBarcodeId\": \""+employeeBarcodeId+"\"," +
				"  \"ClubId\": "+clubId+"," + 
				"  \"Amount\": "+amount+"," + 
				"  \"EffectiveDate\": \""+effectiveDate+"\"," + 
				"  \"PaymentDescription\": \""+paymentDescription+"\"," + 
				"  \"PaymentCategory\": \""+paymentCategory+"\"," + 
				"  \"InvoiceId\": "+invoiceId+"" + 
				"}";
		
		return payload;
	}
	
	public static String takePaymentWithCardOnFileForMemberWithoutInvoice
		(String accountId,
		String customerId,
		String employeeBarcodeId,
		String clubId,
		String amount,
		String effectiveDate,
		String paymentDescription,
		String paymentCategory
		) {
		
		
		String payload = "{\r\n" + 
				"  \"AccountId\": \""+accountId+"\"," + 
				"  \"CustomerId\": \""+customerId+"\"," +
				"  \"EmployeeBarcodeId\": \""+employeeBarcodeId+"\"," +
				"  \"ClubId\": "+clubId+"," + 
				"  \"Amount\": "+amount+"," + 
				"  \"EffectiveDate\": \""+effectiveDate+"\"," + 
				"  \"PaymentDescription\": \""+paymentDescription+"\"," + 
				"  \"PaymentCategory\": \""+paymentCategory+"\"," + 
				"}";
		
	return payload;
}
		

}
