package com.tmonet.kmsp.common;

public class ObjectType {
	/***************************************************************/
	/** Object Types. KMIP Object Name = KMS keyObjectType Values **/
	/***************************************************************/
	static String Certificate = "";
	static String CertificateRequest = "";
	static String OpaqueObject = "";
	static String PGPKey = "";
	static String PrivateKey = "TP.KEY.01";
	static String PublicKey = "TP.KEY.02";
	static String SecretData = "";
	static String SplitKey = "TP.KEY.04";
	static String SymmetricKey = "TP.KEY.03";
	
	/*****************************************/
	/** KMS keyObjectType & KMIP ObjectType **/
	/*****************************************/
	public static String getKMSKeyObjectType(String kmipObjectType) {
		switch(kmipObjectType) {
			case "Certificate" :
				return Certificate;
			
			case "CertificateRequest" :
				return CertificateRequest;
				
			case "OpaqueObject" :
				return OpaqueObject;
				
			case "PGPKey" :
				return PGPKey;
				
			case "PrivateKey" :
				return PrivateKey;
				
			case "PublicKey" :
				return PublicKey;
				
			case "SecretData" :
				return SecretData;
				
			case "SplitKey" :
				return SplitKey;
				
			case "SymmetricKey" :
				return SymmetricKey;
		}
		return "Nothing";
	}
	
	public static String getKMIPObjectType(String kmsKeyObjectType) {
		switch(kmsKeyObjectType) {
			case "TP.KEY.01" :
				return "PrivateKey";
			
			case "TP.KEY.02" :
				return "PublicKey";
				
			case "TP.KEY.03" :
				return "SymmetricKey";
			
			case "TP.KEY.04" :
				return "SplitKey";
		}
		return "Nothing";
	}
}