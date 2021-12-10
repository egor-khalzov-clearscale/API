package payloads;

import resources.base;

public class MemberPL extends base {
	
	public static String authenticateMemberByUserCredentials
		(
		String username,
		String password
		) {
	
	String payload = "{"+
			  "\"Username\": \""+username+"\","+
			  "\"Password\": \""+password+"\""+
			"}";
	return payload;	
	}
	
	public static String createMember
		(
		int nextMemberId,
		String HomeClubId,
		String FirstName,
		String LastName,
		String MembershipTypeId,
		String Title,
		String MiddleInitial,
		String Address1,
		String Address2,
		String City,
		String State,
		String PostalCode,
		String Gender,
		String DateOfBirth,
		String HomePhone,
		String OkToContactHomePhone,
		String MobilePhone,
		String OkToContactMobilePhone,
		String WorkPhone,
		String OkToContactWorkPhone,
		String PreferredPhoneType,
		String EmailAddress,
		String OkToContactEmailAddress,
		String DoNotMail,
		String DoNotMarket,
		String SocialSecurityNumber,
		String DriverLicense,
		String Occupation,
		String Employer,
		String HeadOfHousehold,
		String IncomeChoiceId,
		String PriorityId
		) {
	
	String payload = "{\r\n" + 
			"  \"MemberId\": \""+nextMemberId+"\",\r\n" + 
			"  \"HomeClubId\": "+HomeClubId+",\r\n" + 
			"  \"FirstName\": \""+FirstName+"\",\r\n" + 
			"  \"LastName\": \""+LastName+"\",\r\n" + 
			"  \"MembershipTypeId\": \""+MembershipTypeId+"\",\r\n" + 
			"  \"Title\": \""+Title+"\",\r\n" + 
			"  \"MiddleInitial\": \""+MiddleInitial+"\",\r\n" + 
			"  \"Address1\": \""+Address1+"\",\r\n" + 
			"  \"Address2\": \""+Address2+"\",\r\n" + 
			"  \"City\": \""+City+"\",\r\n" + 
			"  \"State\": \""+State+"\",\r\n" + 
			"  \"PostalCode\": \""+PostalCode+"\",\r\n" + 
			"  \"Gender\": \""+Gender+"\",\r\n" + 
			"  \"DateOfBirth\": \""+DateOfBirth+"\",\r\n" + 
			"  \"HomePhone\": \""+HomePhone+"\",\r\n" + 
			"  \"OkToContactHomePhone\": \""+OkToContactHomePhone+"\",\r\n" + 
			"  \"MobilePhone\": \""+MobilePhone+"\",\r\n" + 
			"  \"OkToContactMobilePhone\": \""+OkToContactMobilePhone+"\",\r\n" + 
			"  \"WorkPhone\": \""+WorkPhone+"\",\r\n" + 
			"  \"OkToContactWorkPhone\": \""+OkToContactWorkPhone+"\",\r\n" + 
			"  \"PreferredPhoneType\": \""+PreferredPhoneType+"\",\r\n" + 
			"  \"EmailAddress\": \""+EmailAddress+"\",\r\n" + 
			"  \"OkToContactEmailAddress\": \""+OkToContactEmailAddress+"\",\r\n" + 
			"  \"DoNotMail\": \""+DoNotMail+"\",\r\n" + 
			"  \"DoNotMarket\": \""+DoNotMarket+"\",\r\n" + 
			"  \"SocialSecurityNumber\": \""+SocialSecurityNumber+"\",\r\n" + 
			"  \"DriverLicense\": \""+DriverLicense+"\",\r\n" + 
			"  \"Occupation\": \""+Occupation+"\",\r\n" + 
			"  \"Employer\": \""+Employer+"\",\r\n" + 
			"  \"HeadOfHousehold\": "+HeadOfHousehold+",\r\n" + 
			"  \"IncomeChoiceId\": \""+IncomeChoiceId+"\",\r\n" + 
			"  \"PriorityId\": \""+PriorityId+"\",\r\n" + 
			"}";
	return payload;	
	}
	
	public static String modifyCardOnFileByMemberAllFields(
			String accountId,
			String customerId,
			String cardNumber,
			String expirationMonth,
			String expirationYear,
			String cardHolderName,
			String addressIsSameAsMemberAddress,
			String addressLine1,
			String addressLine2,
			String city,
			String stateProvince,
			String postalCode,
			String useInPos,
			String setAsHouseAccount
			) {
		
		String payload = "	{\r\n" + 
				"  \"AccountId\": \""+accountId+"\",\r\n" + 
				"  \"CustomerId\": \""+customerId+"\",\r\n" + 
				"  \"CardNumber\": \""+cardNumber+"\",\r\n" + 
				"  \"ExpirationDate\": {\r\n" + 
				"    \"Month\": "+expirationMonth+",\r\n" + 
				"    \"Year\": "+expirationYear+"\r\n" + 
				"  },\r\n" + 
				"  \"CardHolderName\": \""+cardHolderName+"\",\r\n" + 
				"  \"AddressIsSameAsMemberAddress\": "+addressIsSameAsMemberAddress+",\r\n" + 
				"  \"Address\": {\r\n" + 
				"    \"AddressLine1\": \""+addressLine1+"\",\r\n" + 
				"    \"AddressLine2\": \""+addressLine2+"\",\r\n" + 
				"    \"City\": \""+city+"\",\r\n" + 
				"    \"StateProvince\": \""+stateProvince+"\",\r\n" + 
				"    \"PostalCode\": \""+postalCode+"\",\r\n" + 
				"    \"Country\": \"null\"\r\n" + 
				"  },\r\n" + 
				"  \"UseInPos\": "+useInPos+",\r\n" + 
				"  \"SetAsHouseAccount\": "+setAsHouseAccount+"\r\n" + 
				"}";
		
		return payload;	
	}
	
	public static String modifyCardOnFileByMemberCardNumberOnly(
			String accountId,
			String customerId,
			String cardNumber,
			String addressIsSameAsMemberAddress
			) {
		
		String payload = "	{\r\n" + 
				"  \"AccountId\": "+accountId+",\r\n" + 
				"  \"CustomerId\": "+customerId+",\r\n" + 
				"  \"CardNumber\": \""+cardNumber+"\",\r\n" + 
				"  \"AddressIsSameAsMemberAddress\": "+addressIsSameAsMemberAddress+"\r\n" + 
				"}";
		
		return payload;	
	}
	
	public static String addCardOnFileByMemberCardNumberOnly(
			String updateActiveAgreements,
			String customerId,
			String cardNumber,
			String expirationMonth,
			String expirationYear,
			String cardHolderName,
			String addressIsSameAsMemberAddress,
			String addressLine1,
			String addressLine2,
			String city,
			String stateProvince,
			String postalCode,
			String useInPos,
			String setAsHouseAccount
			) {
		
		String payload = "{\r\n" + 
				"  \"UpdateActiveAgreements\": \""+updateActiveAgreements+"\",\r\n" + 
				"  \"CustomerId\": \""+customerId+"\",\r\n" + 
				"  \"CardNumber\": \""+cardNumber+"\",\r\n" + 
				"  \"ExpirationDate\": {\r\n" + 
				"    \"Month\": \""+expirationMonth+"\"," + 
				"    \"Year\": "+expirationYear+"\r\n" + 
				"  },\r\n" + 
				"  \"CardHolderName\": \""+cardHolderName+"\",\r\n" + 
				"  \"AddressIsSameAsMemberAddress\": "+addressIsSameAsMemberAddress+",\r\n" + 
				"  \"Address\": {\r\n" + 
				"    \"AddressLine1\": \""+addressLine1+"\",\r\n" + 
				"    \"AddressLine2\": \""+addressLine2+"\",\r\n" + 
				"    \"City\": \""+city+"\",\r\n" + 
				"    \"StateProvince\": \""+stateProvince+"\",\r\n" + 
				"    \"PostalCode\": \""+postalCode+"\",\r\n" + 
				"    \"Country\": \"null\"\r\n" + 
				"  },\r\n" + 
				"  \"UseInPos\": "+useInPos+",\r\n" + 
				"  \"SetAsHouseAccount\": "+setAsHouseAccount+"\r\n" + 
				"}";
		
		return payload;	
	}

	public static String getMemberToken(String barcodeId, String expirationTimeSpan) {
	
	String payload = "{\r\n"
			+ "  \"BarcodeId\": \""+barcodeId+"\",\r\n"
			+ "  \"ExpirationTimeSpan\": \""+expirationTimeSpan+"\"\r\n"
			+ "}";
	return payload;	
	}
	
}
