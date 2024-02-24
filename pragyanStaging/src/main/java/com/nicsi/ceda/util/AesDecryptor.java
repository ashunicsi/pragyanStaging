package com.nicsi.ceda.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AesDecryptor 
{
	public String decryptJavaAPIData(String encryptedText, String key) throws Exception 
    {
    	SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Decrypt Data
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes);

    	 return decryptedText;
    }
	public String decryptDotNetAPIData(String encryptedText, String key) throws UnsupportedEncodingException 
    {
    	
    	byte[] decryptedBytes = null;
    	try
    	{
	    	byte[] combinedBytes = Base64.getDecoder().decode(encryptedText);
	
	        byte[] iv = new byte[16];
	        System.arraycopy(combinedBytes, 0, iv, 0, 16);
	
	        byte[] encryptedBytes = new byte[combinedBytes.length - 16];
	        System.arraycopy(combinedBytes, 16, encryptedBytes, 0, encryptedBytes.length);
	
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
	
	       decryptedBytes = cipher.doFinal(encryptedBytes);
	       
    	}
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	 return new String(decryptedBytes, "UTF-8");
    }
}
