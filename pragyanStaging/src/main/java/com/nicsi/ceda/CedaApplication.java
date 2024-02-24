package com.nicsi.ceda;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.SerializationUtils;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableSwagger2
@EnableScheduling // Enable Spring's scheduling capabilities
public class CedaApplication {

	public static void main(String[] args) throws Exception 
	{
		 SpringApplication.run(CedaApplication.class, args);
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode("Gvsnm@123");
			//System.out.println(decryptPHPData("IS3n5fIv7k42EnLHK2jYzusfTIh27he5xAgoU25IjjUilFoq2CQwB/cEnP1R4+9k","15d9ae790856424db9aa91c637090166"));
			//System.out.println(decryptPHPData("yC0ehD756SZhl4lTt5lMqF9BQGBPtEGJY49ELMOyRG0rESnKU7ED362uTEDqTC9a","15d9ae790856424db9aa91c637090166"));
			//System.out.println(decryptPHPData("MNJ/FJ1xH22QaZXWqtGWN/6Pq3Icq5HHHOwuSytAcyTA8il0oQWFGscbNY0kSxCE","15d9ae790856424db9aa91c637090166"));
			//System.out.println(encodedPassword);
		 //String key = "0123456789abcdef"; // 128-bit key
	     //   String iv = "0123456789abcdef";  // 16-byte IV
	      //  String plaintext = "Adityakarn1!";
	       // String ciphertext = encrypt(key, iv, plaintext);
	        //System.out.println("Encrypted: " + ciphertext);
		//String encryptedText = "zvwXzB5cWt5On3onrUh2zpn1wtyOsLrYtFdszeanU7HyKoCXT/8vlN3i8wvpx1qCWHHG8PvxKqHLYP2RAxEHHA8wPwapQqYFA3QKkwCp9KI=";
		//String encryptedText = "MdournW+3C8bvBCBROpR3g==";
			/*
			 * String encryptedText1 =
			 * "283423c261c8df8983cb3262db49a3b7c0fb6909d75cd8693b95ba39d36886e4"; String
			 * encryptedText2 = "3TAOeGJ1zFJCeF/BbWielIRgralMblM4Wo5f+NWnhx0="; String
			 * encryptedText3 = "L8bC3oPsSxWhIleAL3XRZwJTivZDD+/GtEI2ub73uwY="; String
			 * encryptedText4 = "Xwsa3juVXLuNl4rxF+a8VGfzCu81YbpiKftaVgqDuzc="; String key =
			 * "097bf83dc59b4d8a85f4674b9ea1cb7e"; // 32-character key for AES-256
			 * 
			 * String decryptedText = decrypt(encryptedText1, key);
			 * System.out.println("Decrypted Text: " + decryptedText); decryptedText =
			 * decrypt(encryptedText2, key); System.out.println("Decrypted Text: " +
			 * decryptedText); decryptedText = decrypt(encryptedText3, key);
			 * System.out.println("Decrypted Text: " + decryptedText); decryptedText =
			 * decrypt(encryptedText4, key); System.out.println("KPI Vazlue: " +
			 * decryptedText);
			 */
        
        //System.out.println(decryptJava(encryptedText, key));
        //String decryptedText = decrypt(encryptedText, key);
        //System.out.println("Decrypted Text: " + decryptedText);
		//System.out.println("Pragyan Application Started...");
		//SpringApplication.run(CedaApplication.class, args);
		/*byte[] salt = generateSalt();

        // The password you want to hash
        String password = "Adityakarn1!";

        // Number of iterations (recommended: at least 100,000 or more)
        int iterations = 600000;

        // Key length (in bits)
        int keyLength = 256; // 256 bits for SHA-256

        // Generate the PBKDF2 hash
        String hashedPassword = generatePBKDF2Hash(password, salt, iterations, keyLength);

        System.out.println("Salt (Base64): " + Base64.getEncoder().encodeToString(salt));
        System.out.println("PBKDF2 Hash: " + hashedPassword);
        
        password = "MySecurePassword";
        SecureRandom random = new SecureRandom();
        salt = new byte[16];
        random.nextBytes(salt);
        iterations = 600000;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(keySpec).getEncoded();
        String result = Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(hash);
        System.out.println(result);
		  String pass = "mW4Iu2gRVjWURFJU$2d8cad0ecd18e1ca56551e8d3af3f9d53f5c3ceef76414c246af2ca530acb3ad";
		  String storedSaltBase64 = "StoredSaltInBase64"; // Replace with your stored salt
	        String storedHashBase64 = "MmVBR5g5eKFo7IhFA3R12A==$eKIo11A6eYJ5PBl17O5DCLJpSy36TqkFqtXaP+DbOjM="; // Replace with your stored hash
	        String userPassword = "Adityakarn1!"; // Replace with the user-provided password
	        
	        // Decode the stored salt and hash from Base64
	        byte[] storedSalt = Base64.getDecoder().decode(storedSaltBase64);
	        byte[] storedHash = Base64.getDecoder().decode(storedHashBase64);
	        
	        // Set the same number of iterations used during hashing
	        iterations = 600000;
	        
	        // Create a PBEKeySpec with the user-provided password, stored salt, and iterations
	         keySpec = new PBEKeySpec(userPassword.toCharArray(), storedSalt, iterations, 256);
	        
	        // Generate the secret key using PBKDF2 with HMAC-SHA256
	         factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        byte[] computedHash = factory.generateSecret(keySpec).getEncoded();
	        
	        // Compare the computed hash with the stored hash
	        boolean passwordMatches = MessageDigest.isEqual(computedHash, storedHash);
	        
	        if (passwordMatches) {
	            System.out.println("Password is correct.");
	        } else {
	            System.out.println("Password is incorrect.");
	        }
	        */
		//System.out.println(encrypt("Ashu Aditya", "15d9ae790856424db9aa91c637090166"));
		//System.out.println(decryp(encrypt("Ashu Aditya", "15d9ae790856424db9aa91c637090166"), "15d9ae790856424db9aa91c637090166"));
		//System.out.println(decryp("tLWR3kAwEqIvMYf9owH0qHO0DypNN3bJl2glSoc+zsU=", "15d9ae790856424db9aa91c637090166"));
		 
		// Set your encrypted data and key
		 //String key = "15d9ae790856424db9aa91c637090166";
	     ///   String encryptedData = "tDMZXIUwVHTOFWojvTvKFM2lQCjlUtWFwrfjK/CPkjg=";
	       // String decryptedData = decryptData(encryptedData, key);
	       // System.out.println("Decrypted data Ashu : " + decryptedData);
	    /*
	        <?php

	        		function encryptData($data, $key) 
	        		{
	        		    // Generate an initialization vector (IV)
	        		    $iv = openssl_random_pseudo_bytes(openssl_cipher_iv_length('aes-256-cbc'));

	        		    // Encrypt the data using AES-256-CBC
	        		    $encryptedData = openssl_encrypt($data, 'aes-256-cbc', $key, OPENSSL_RAW_DATA, $iv);

	        		    // Combine IV and encrypted data
	        		    $encryptedMessage = base64_encode($iv . $encryptedData);

	        		    return $encryptedMessage;
	        		}

	        		// Example usage:
	        		$key = "15d9ae790856424db9aa91c637090166";
	        		$data = "NICSI-Pragyan";
	        		$encryptedData = encryptData($data, $key);
	        		echo "Encrypted data: " . $encryptedData;

	        		?>
	    */
			try
			{
				System.out.println(decryptData1("/tvP4LdCyWSOsFDzXelOD/BXlyaz6+ImFZLSd4c7oAmlyEjs92RbQCRf04XRW2nL", "15d9ae790856424db9aa91c637090166"));
				String originalString = decryptData1("/tvP4LdCyWSOsFDzXelOD/BXlyaz6+ImFZLSd4c7oAmlyEjs92RbQCRf04XRW2nL", "15d9ae790856424db9aa91c637090166");

		        // Trim the string after the first 10 characters
		        String trimmedString = originalString.substring(0, Math.min(originalString.length(), 10));
		        String pp = trimmedString  + " 00:00:00";
		        System.out.println(pp);
		        System.out.println("Original string: " + originalString);
		        System.out.println("Trimmed string: " + trimmedString);
		        
			}
			catch (Exception e) 
			{
				System.out.println("DO Decryption....");
			}
	}
	public static String decryptData1(String encryptedData, String key) throws Exception 
	{
        // Decode the Base64 encoded string
        byte[] encryptedMessage = Base64.getDecoder().decode(encryptedData);

        // Extract the IV and encrypted data
        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[encryptedMessage.length - 16];
        System.arraycopy(encryptedMessage, 0, iv, 0, 16);
        System.arraycopy(encryptedMessage, 16, encryptedBytes, 0, encryptedBytes.length);

        // Create the secret key and initialization vector
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Decrypt the data using AES-256-CBC
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

	private static byte[] generateRandomBytes(int length) 
	{
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    // Helper method to convert byte array to hexadecimal string
    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
	 public static byte[] hexStringToByteArray(String hexString)
	 {
	        int len = hexString.length();
	        byte[] data = new byte[len / 2];
	        for (int i = 0; i < len; i += 2) 
	        {
	            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i+1), 16));
	        }
	        return data;
	    }
	public static String encrypt(String plaintext, String key) throws Exception 
	{
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes());
        String encryptedDataString = Base64.getEncoder().encodeToString(encryptedData);
        return encryptedDataString;
    }
	static String decryp(String encryptedText, String key) throws Exception 
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
	public static Object decryptJavaAPIData(String encryptedText, String key) throws Exception 
	{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText.getBytes("UTF-8"));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return SerializationUtils.deserialize(bytes);
    }
	@Bean
	public Docket swagger() 
	{
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nicsi.ceda")).build();
	}
	
   
    // Function to create a PBKDF2 password hash
    public static String createPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
        int iterations = 600000; // Number of iterations
        int keyLength = 256;    // Key length in bits

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Convert the salt and hash to Base64 strings
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        return "pbkdf2:sha256:" + iterations + "$" + saltBase64 + "$" + hashBase64;
    }

    // Function to verify a password
    public static boolean verifyPassword(String enteredPassword, String storedHash) throws NoSuchAlgorithmException, InvalidKeySpecException 
    {
    	//System.out.println(enteredPassword);
        String[] parts = storedHash.split("\\$");
        if (parts.length != 3) 
        {
            return false;
        }
       // System.out.println(parts[0]);
        //int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] storedHashBytes = Base64.getDecoder().decode(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, 600000, storedHashBytes.length * 8);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] computedHash = factory.generateSecret(spec).getEncoded();

        // Compare the computed hash with the stored hash
        return MessageDigest.isEqual(storedHashBytes, computedHash);
    }
    static String decryptData(String encryptedText, String key) throws UnsupportedEncodingException 
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
    	//System.out.println("decryptedBytes = "+new String(decryptedBytes, "UTF-8"));
    	 return new String(decryptedBytes, "UTF-8");
    }
    static String decrypt(String encryptedText, String key) throws UnsupportedEncodingException 
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
    static String decryptJava(String encryptedText, String key) throws Exception 
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
    public static String encrypt(String key, String iv, String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    static String decryptPHPData(String encryptedText, String key) throws UnsupportedEncodingException 
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
    	//System.out.println("decryptedBytes = "+new String(decryptedBytes, "UTF-8"));
    	 return new String(decryptedBytes, "UTF-8");
    }
}

