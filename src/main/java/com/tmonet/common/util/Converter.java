package com.tmonet.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmonet.kms.mgmt.common.exception.KMSErrorCode;
import com.tmonet.kms.mgmt.common.exception.KMSException;

@Component
public class Converter {

    private static ObjectMapper mapper = new ObjectMapper();
    
    public static byte[] hexStringToByteArray(String source) {
        int len = source.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(source.charAt(i), 16) << 4)
                                 + Character.digit(source.charAt(i+1), 16));
        }
        return data;
    }
    
    public static String byteArrayToHexString(byte[] source) {
        if (source == null) return "";
        
        StringBuilder sb = new StringBuilder();
        for(final byte value: source) {
            sb.append(String.format("%02X", value & 0xff));
        }
        
        return sb.toString();
    }
    
    public static String byteArrayToHexString(byte[] source, int formatType) {
        if (source == null) return "";
        
        StringBuilder sb = new StringBuilder();
        String formatPrefix = "";
        String formatSeparator = "";
        
        if (formatType > 1) {
            sb.append("[");
        }
        
        for (int idx = 0; idx < source.length; idx++) {
            if (formatType == 1) {
                formatPrefix = "0x";
                formatSeparator = " ";
            }
            
            sb.append(String.format(formatPrefix + "%02X" + formatSeparator, source[idx] & 0xff));
            
            if (formatType == 2) {
                if ((idx + 1) % 4 == 0) {
                    if (idx + 1 == source.length) break;
                    sb.append(" ");
                }
            }
        }
        
        if (formatType > 1) {
            sb.append("]");
        }
        
        return sb.toString();
    }
    
    public static String base64Encode(byte[] source) {
        Encoder b64 = Base64.getEncoder();
        return new String(b64.encode(source));
    }
    
    public static String base64Decode(byte[] source) {
        Decoder b64 = Base64.getDecoder();
        return new String(b64.decode(source));
    }
    
    public static byte[] base64Decode(String source) {
        Decoder b64 = Base64.getDecoder();
        return b64.decode(source);
    }
    
    public static String toPrettyString(Object source) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return source.toString();
        }
    }
    
    public static String sha256Encode(String source) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(source.getBytes());
			byte byteData[] = md.digest();
			return byteArrayToHexString(byteData);
		} catch (Exception e) {
			throw new KMSException(KMSErrorCode.HSM_SERVER_ERROR);
		}
    }
}
