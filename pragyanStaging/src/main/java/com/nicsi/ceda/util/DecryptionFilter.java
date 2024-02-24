package com.nicsi.ceda.util;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class DecryptionFilter
{
/*extends OncePerRequestFilter {

    private final String secretKey = "0a038bd98ea24fe3af4ab8bc01370527"; // Same secret key used for encryption

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
    {
        // Decrypt URL parameters before forwarding the request to the controller
    	System.out.println("URL = "+request.getRequestURI().toString());
        try 
        {
			decryptUrlParameters(request);
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        filterChain.doFilter(request, response);
    }

    private void decryptUrlParameters(HttpServletRequest request) throws Exception 
    {
        // Get encrypted URL parameters from request
        String encryptedData = request.getRequestURI().toString().substring(1);
        System.out.println("Encrypted Request = "+encryptedData);
        if (encryptedData != null) 
        {
            //String decryptedData = decryptJava("U2FsdGVkX18LQcOl2/TDRN+SZwMzuPm8ZlTEBkWvgBQ=", "86e26d46b8684a18828323f7f3cd30ac").toString();
            //System.out.println("decrypted URL = "+decryptedData);
            System.out.println("decryptedData = "+decryptJava("pYIRvtcqUiGJ2K5AZBLMAFSG0rvb1+m52npA+/5Wr5I=", secretKey));
          System.out.println("decryptedData = "+decryptJava("yaGcK9kjaUbSf2z3k78+yg==", secretKey));
            System.out.println("decryptedData = "+decryptJava("78Gup3hywVed2k3xlIiqnA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("u18PTeZkSYdcEIhgBUzfzw==", secretKey));
            System.out.println("decryptedData = "+decryptJava("u18PTeZkSYdcEIhgBUzfzw==", secretKey));
            System.out.println("decryptedData = "+decryptJava("u18PTeZkSYdcEIhgBUzfzw==", secretKey));
            System.out.println("decryptedData = "+decryptJava("Iqy2ipAC+4VQVIW8jnzPug==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("gxBjjEta4bD/kwkYT8oZtw==", secretKey));
            System.out.println("decryptedData = "+decryptJava("EbcpU03KnMx3zViGnZv+1g==", secretKey));
            System.out.println("decryptedData = "+decryptJava("Q51hx2eSTSOA+4crBLPlIA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("3hHRO6H8H4KQUNnxG/wlbA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("/q0qMDhCs+087C7NtNq9cA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("XhKlAOdzgbJaSot0moSO0Q==", secretKey));
            
            System.out.println("decryptedData = "+decryptJava("WyqGjLclEEYLpuZzhXBekA==", secretKey));
            System.out.println("decryptedData = "+decryptJava("BnhVCRlvA0MeZSNNEJhmow==", secretKey));
            System.out.println("decryptedData = "+decryptJava("lj35xIT/3a6p9ohQqmBJk3ClC71PkYjiN2NDHmr0zjk=", secretKey));
            System.out.println("decryptedData = "+decryptJava("HaBn4RH/1qkwThKafB8mZ1SG0rvb1+m52npA+/5Wr5I=", secretKey));
            
           
            
            request.setAttribute("decryptedData", "");
        }
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
    */
}