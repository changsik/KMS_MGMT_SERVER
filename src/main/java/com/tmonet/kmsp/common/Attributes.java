package com.tmonet.kmsp.common;

public class Attributes {
	
	/**********************************************/
	/** Attribute Names. KMIP names = KMS names. **/
	/**********************************************/	
	static String ActivationDate = "";  //Not exist in timonet KMS
	static String CryptographicAlgorithm = "cryptographicAlgorithm";
	static String CryptographicLength = "keyLength";
	static String CryptographicUsageMask = "keyUsageMask";
	static String Extractable = "canExportKey";
	
	/****************************************************************/
	/** CryptographicAlgorithm Values. KMIP variables = KMS values **/
	/****************************************************************/
	//KMIP and KMS are both same exactly...
	
	/*****************************************************************/
	/** CryptographicUsageMask Values. KMIP variables = KMS values. **/
	/*****************************************************************/
	static String Sign = "00000001";
	static String Verify = "00000002";
	static String Encrypt = "00000004";
	static String Decrypt = "00000008";
	static String WrapKey = "00000010";
	static String UnwrapKey = "00000020";
	static String MACGenerate = "00000080";
	static String MACVerify = "00000100";
	static String DeriveKey = "00000200";
	static String KeyAgreement = "00000800";
	static String CertificateSign = "00001000";
	static String CRLSign = "00002000";
	static String Authenticate = "00100000";
	static String Unrestricted = "00200000";
	static String FPEEncrypt = "00400000";
	static String FPEDecrypt = "00800000";
	
	/*****************************************************/
	/** Extractable Values. KMIP variables = KMS values **/
	/*****************************************************/
	static String False = "0";
	static String True = "1"; //default value in KMIP.
		
	/********************************************************/
	/** Get KMS Attribute Names From KMIP Attribute Names. **/
	/********************************************************/
	public static String getKMSAttrName(String kmipAttrName) {
		switch(kmipAttrName) {
			case "CryptographicAlgorithm" :
				return CryptographicAlgorithm;
			
			case "CryptographicUsageMask" :
				return CryptographicUsageMask;
				
			case "CryptographicLength" :
				return CryptographicLength;
				
			case "ActivationDate" :
				return ActivationDate;
				
			case "Extractable" :
				return Extractable;
				
			case "objectType" :
				return "keyObjectType";
		}
		return "Nothing";
	}
	
	public static String getKMIPAttrName(String kmsAttrName) {
		switch(kmsAttrName) {
			case "canExportKey" :
				return "Extractable";
			
			case "cryptographicAlgorithm" :
				return "CryptographicAlgorithm";
				
			case "keyLength" :
				return "CryptographicLength";
				
			case "keyObjectType" :
				return  "ObjectType";
				
			case "keyUsageMask" :
				return "CryptographicUsageMask";
				
			case "" :
				return "ActivationDate";
		}
		return "Nothing";
	}
	
	/**********************************************************/
	/** Get KMS KeyUsageMask Values & KMIP UsageMask Values. **/
	/**********************************************************/
	public static String getKMSKeyUsageMaskValues(String kmipMasks) {
		String[] masks = kmipMasks.split(" ");
		int usageMasks = 0;
		
		for(int i=0; i<masks.length; i++) {
			switch(masks[i]) {
				case "Sign" :
					usageMasks = usageMasks | Integer.parseInt(Sign, 16);
					continue;
				case "Verify" :
					usageMasks = usageMasks | Integer.parseInt(Verify, 16);
					continue;
				case "Encrypt" :
					usageMasks = usageMasks | Integer.parseInt(Encrypt, 16);
					continue;
				case "Decrypt" :
					usageMasks = usageMasks | Integer.parseInt(Decrypt, 16);
					continue;
				case "WrapKey" :
					usageMasks = usageMasks | Integer.parseInt(WrapKey, 16);
					continue;
				case "UnwrapKey" :
					usageMasks = usageMasks | Integer.parseInt(UnwrapKey, 16);
					continue;
				case "MACGenerate" :
					usageMasks = usageMasks | Integer.parseInt(MACGenerate, 16);
					continue;
				case "MACVerify" :
					usageMasks = usageMasks | Integer.parseInt(MACVerify, 16);
					continue;
				case "DeriveKey" :
					usageMasks = usageMasks | Integer.parseInt(DeriveKey, 16);
					continue;
				case "KeyAgreement" :
					usageMasks = usageMasks | Integer.parseInt(KeyAgreement, 16);
					continue;
				case "CertificateSign" :
					usageMasks = usageMasks | Integer.parseInt(CertificateSign, 16);
					continue;
				case "CRLSign" :
					usageMasks = usageMasks | Integer.parseInt(CRLSign, 16);
					continue;
				case "Authenticate" :
					usageMasks = usageMasks | Integer.parseInt(Authenticate, 16);
					continue;
				case "Unrestricted" :
					usageMasks = usageMasks | Integer.parseInt(Unrestricted, 16);
					continue;
				case "FPEEncrypt" :
					usageMasks = usageMasks | Integer.parseInt(FPEEncrypt, 16);
					continue;
				case "FPEDecrypt" :
					usageMasks = usageMasks | Integer.parseInt(FPEDecrypt, 16);
					continue;
					
			}
		}
		
		return Integer.toHexString(usageMasks);
	}
	
	/*
	static String Sign = "00000001";
	static String Verify = "00000002";
	static String Encrypt = "00000004";
	static String Decrypt = "00000008";
	static String WrapKey = "00000010";
	static String UnwrapKey = "00000020";
	static String MACGenerate = "00000080";
	static String MACVerify = "00000100";
	static String DeriveKey = "00000200";
	static String KeyAgreement = "00000800";
	static String CertificateSign = "00001000";
	static String CRLSign = "00002000";
	static String Authenticate = "00100000";
	static String Unrestricted = "00200000";
	static String FPEEncrypt = "00400000";
	static String FPEDecrypt = "00800000";
	*/
	
	public static String getKMIPCryptographicUsageMaskValues(String kmsMask) {
		int masks = Integer.parseInt(kmsMask, 16);
		String KMIPMasks = "";
		
		int sign = Integer.parseInt(Sign, 16);
		int verify = Integer.parseInt(Verify, 16);
		int encrypt = Integer.parseInt(Encrypt, 16);
		int decrypt = Integer.parseInt(Decrypt, 16);
		int wrapkey = Integer.parseInt(WrapKey, 16);
		int unwrapkey = Integer.parseInt(UnwrapKey, 16);
		int macgenerate = Integer.parseInt(MACGenerate, 16);
		int macverify = Integer.parseInt(MACVerify, 16);
		int derivekey = Integer.parseInt(DeriveKey, 16);
		int keyagreement = Integer.parseInt(KeyAgreement, 16);
		int certificatesign = Integer.parseInt(CertificateSign, 16);
		int crlsign = Integer.parseInt(CRLSign, 16);
		int authenticate = Integer.parseInt(Authenticate, 16);
		int unrestricted = Integer.parseInt(Unrestricted, 16);
		int fpeencrypt = Integer.parseInt(FPEEncrypt, 16);
		int fpedecrypt = Integer.parseInt(FPEDecrypt, 16);

		if( sign == (sign & masks) )
			KMIPMasks += "Sign ";
		if( verify == (verify & masks) )
			KMIPMasks += "Verify ";
		if( encrypt == (encrypt & masks) )
			KMIPMasks += "Encrypt ";
		if( decrypt == (decrypt & masks) )
			KMIPMasks += "Decrypt ";
		if( wrapkey == (wrapkey & masks) )
			KMIPMasks += "WrapKey ";
		if( unwrapkey == (unwrapkey & masks) )
			KMIPMasks += "UnwrapKey ";
		if( macgenerate == (macgenerate & masks) )
			KMIPMasks += "MacGenerate ";
		if( macverify == (macverify & masks) )
			KMIPMasks += "MacVerify ";
		if( derivekey == (derivekey & masks) )
			KMIPMasks += "DeriveKey ";
		if( keyagreement == (keyagreement & masks) )
			KMIPMasks += "KeyAgreement ";
		if( certificatesign == (certificatesign & masks) )
			KMIPMasks += "CertificateSign ";
		if( crlsign == (crlsign & masks) )
			KMIPMasks += "CRLSign ";
		if( authenticate == (authenticate & masks) )
			KMIPMasks += "Authenticate ";	
		if( unrestricted == (unrestricted & masks) )
			KMIPMasks += "Unrestricted ";
		if( fpeencrypt == (fpeencrypt & masks) )
			KMIPMasks += "FPEEncrypt ";
		if( fpedecrypt == (fpedecrypt & masks) )
			KMIPMasks += "FPEDecrypt ";
				
		return KMIPMasks;
	}
	
	/*******************************************/
	/** CanExportKey Values & Extrator Values **/
	/*******************************************/
	public static String getKMSCanExportValues(String kmipExtractable) {
		switch(kmipExtractable) {
			case "TRUE" :
				return "1";
				
			case "FALSE" :
				return "0";
				
			default :
				return "1"; //default value in KMIP.
		}
	}
	
	public static String getKMIPExtratorValues(String kmsCanExport) {
		switch(kmsCanExport) {
			case "1" :
				return "TRUE";
				
			case "0" :
				return "FALSE";
				
			default :
				return "TRUE"; //default value in KMIP.
		}
	}
}