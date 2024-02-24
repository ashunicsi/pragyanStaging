package com.nicsi.ceda.dao.impl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.util.SerializationUtils;

import com.nicsi.ceda.dao.IRegistrationDao;
import com.nicsi.ceda.util.AesEncryptor;
import com.nicsi.ceda.model.ChangePassword;
import com.nicsi.ceda.model.ListOfTokens;
import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.model.Registration;
import com.nicsi.ceda.repository.ChangePasswordRepo;
import com.nicsi.ceda.repository.LoginRepo;
import com.nicsi.ceda.repository.RegistrationRepo;

@Repository
@Transactional
public class RegistrationDaoImpl implements IRegistrationDao
{
	private static final Random generator = new Random();
	static final String SOURCE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom secureRnd = new SecureRandom();

	@Autowired
	private RegistrationRepo regRepo;
	@Autowired
	private ChangePasswordRepo changePassRepo;
	@Autowired
	private LoginRepo loginRepo;
	@Autowired
	private AesEncryptor aes;
	@Autowired
    private JavaMailSender javaMailSender;
	@Autowired
    private Environment environment;
	
	String status = null;
	
	public String registration(Registration reg, ModelMap map, HttpSession session) 
	{
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime lt = LocalDateTime.now(); 
		String createdDate = lt.format(formatter);
		reg.setRegistrationTime(createdDate);
		reg.setStatus(1);
		
		
		regRepo.save(reg);
		// Generate Random Passsword send Email
		session.setAttribute("language", reg.getLanguage());
		String generatedTime = lt.format(formatter);
		String generatedPassword = randomString(8);
		map.addAttribute("email", reg.getEmail());
		
		ChangePassword cp =new ChangePassword();
		cp.setCurrentPassword(generatedPassword);
		cp.setEmailId(reg.getEmail());
		cp.setGeneratedTime(generatedTime);
		changePassRepo.save(cp);
		
		status = "success";
		return status;
	}
	public String changePassword(ChangePassword cp, ModelMap map, HttpSession session)
	{
		Registration reg = regRepo.findByEmail(cp.getEmailId());
		
		if(cp.getEmailId().equals(reg.getEmail()))
		{
			ChangePassword changePass = changePassRepo.findByEmailId(cp.getEmailId());
			if(changePass != null)
			{
				//if(cp.getCurrentPassword().equals(changePass.getCurrentPassword()))
				//{
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String encodedPassword = passwordEncoder.encode(cp.getNewPassword());
					Login log = new Login();
					log.setEmailId(reg.getEmail());
					log.setPassword(encodedPassword);
					log.setName(reg.getName());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					LocalDateTime lt = LocalDateTime.now(); 
					String createdDate = lt.format(formatter);
					log.setCreatedDate(createdDate);
					log.setFlag(1);
					log.setNoOfAttempts(0);
					log.setIsLocked("No");
					log.setLanguage("English");
					log.setUserType("admin");
					log.setUserPermission(1);
					log.setStatus(0);
					log.setIsSuperAdmin(1);
					log.setUserRoleId(1);
					log.setDisplayName("Project Admin");
					log.setIsApproved(0);
					//Generate token
					//String tokenKey = aes.convertToDatabaseColumn(log.getEmailId());
					//log.setTokenKey(tokenKey);
					loginRepo.save(log);
					ListOfTokens token = new ListOfTokens();
					String tokenKey = aes.convertToDatabaseColumn(reg.getEmail());
					reg.setTokenKey(tokenKey);
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String encryptionKey  = uuid.substring(0, Math.min(32, uuid.length()));
			        
			        reg.setEncryptionKey(encryptionKey);
			        //ListOfTokens token = new ListOfTokens();
					//token.set
					//String tokenKey = aes.convertToDatabaseColumn(reg.getEmail());
					regRepo.save(reg);
					
					String jdbcUrl = environment.getProperty("superset-jdbcUrl");
			        String username = environment.getProperty("superset-username");
			        String password = environment.getProperty("superset-password");
			        Date date = new Date();  
			        Timestamp ts=new Timestamp(date.getTime()); 
/*
			        try
			        {
				     
			        	Class.forName("org.postgresql.Driver");
				
				        // Establish the database connection
				        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
				        Statement stmt=connection.createStatement();  
				        String sql = "SELECT COUNT(*) FROM public.ab_user";
				     // Execute the query
			            Statement statement = connection.createStatement();
			            ResultSet resultSet = statement.executeQuery(sql);

			            // Extract the count from the result set
			            int rowCount = 0;
			            if (resultSet.next()) 
			            {
			                rowCount = resultSet.getInt(1);
			            }
						String query = "INSERT INTO public.ab_user (id, first_name, last_name, username, password, active, email, last_login, login_count, fail_login_count, created_on, changed_on, created_by_fk, changed_by_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			            PreparedStatement preparedStatement = connection.prepareStatement(query);
			            preparedStatement.setInt(1, rowCount+1);
			            preparedStatement.setString(2, reg.getName());
			            preparedStatement.setString(3, "");
			            preparedStatement.setString(4, reg.getEmail());
			            preparedStatement.setString(5, "");
			            preparedStatement.setBoolean(6, true);
			            preparedStatement.setString(7, reg.getEmail());
			            preparedStatement.setTimestamp(8, ts);
			            preparedStatement.setInt(9, 0);
			            preparedStatement.setInt(10, 0);
			            preparedStatement.setTimestamp(11, ts);
			            preparedStatement.setTimestamp(12, ts);
			            preparedStatement.setInt(13, 1);
			            preparedStatement.setInt(14, 1);
			            preparedStatement.executeUpdate();
			            //now map user and role
			            sql = "SELECT COUNT(*) FROM public.ab_user_role";
			   	     // Execute the query
			               statement = connection.createStatement();
			               resultSet = statement.executeQuery(sql);

			               // Extract the count from the result set
			               int totalCount = 0;
			               if (resultSet.next()) 
			               {
			            	   totalCount = resultSet.getInt(1);
			               }
			            query = "INSERT INTO ab_user_role (id, user_id, role_id) VALUES (?, ?, ?)";
			            preparedStatement = connection.prepareStatement(query);
			            preparedStatement.setInt(1, totalCount+1);
			            preparedStatement.setInt(2, rowCount+1);
			            preparedStatement.setInt(3, log.getUserPermission());
			           // preparedStatement.executeUpdate();
			            preparedStatement.close();

			            connection.close();
			        }
			        catch (Exception e) 
			        {
						e.printStackTrace();
					}
			        */
					changePassRepo.deleteByEmailId(reg.getEmail());
					//send email for table structure and token key to user
					SimpleMailMessage msg = new SimpleMailMessage();
			        msg.setTo(log.getEmailId());
			        //msg.setCc(log.getEmailId());
			        msg.setSubject("Pragyan Application Table Details");
			        
			        //send sectorCode, ministryCode, departmentCode
			        msg.setText("Respected Sir/Ma'am,\n\nPlease find the details regarding registration of Pragyan Application.\n\n"
			        		+ "Your Token Key is "+tokenKey+" and your password encryption key is "+reg.getEncryptionKey()+"\n\n"
			        				+ "Please create 2 tables in your database as per below syntax.\n\n"
			        				+ "CREATE TABLE  table_data(id integer NOT NULL DEFAULT nextval('table_data_id_seq'::regclass), atmpt integer NOT NULL, data_date timestamp without time zone, dept_code integer, frequency_id integer, from_duration_of_data timestamp without time zone, group_id integer, instance_code integer, kpi_id integer, kpi_value double precision, lvl10_code integer, lvl1_code integer, lvl2_code integer, lvl3_code integer, lvl4_code integer, lvl5_code integer, lvl6_code integer, lvl7_code integer, lvl8_code integer, lvl9_code integer, ministry_code integer, project_code integer, sec_code integer, to_duration_of_data timestamp without time zone, PRIMARY KEY (id))\n\n"
			        				+ "CREATE TABLE table_log id integer NOT NULL DEFAULT nextval('table_log_id_seq'::regclass),  req_end_dt timestamp without time zone, req_start_dt timestamp without time zone, dept_code integer, error_code integer, datadt_from timestamp without time zone, group_id integer, instance_code integer, log_date timestamp without time zone, msg character varying(255) COLLATE pg_catalog.\"default\", ministry_code integer, project_code numeric(19,2), sec_code integer, status integer,  datadt_to timestamp without time zone,PRIMARY KEY (id))\r\n" + 
			        				"\n\n\n"
			        				+ "Thanks Regard\n"
			        				+ "Support Team\n"
			        				+ "Pragyan, NICSI");
			       
			        //javaMailSender.send(msg);
					status = "success";
				//}
				//else
				//{
				//	status = "Wrong Current Password";
				//}
			}
			else
			{
				status = "Invalid Email Id";
			}
		}
		else
		{
			status = "Invalid Email Id";
		}
		return status;
	}
	public String validateUsername(String email)
	{
		
		if(changePassRepo.findByEmailId(email) == null)
		{
			status = "success";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public String validateCurrentPass(String email, String currentPassword)
	{
		
		if(changePassRepo.findByEmailIdAndCurrentPassword(email, currentPassword) == null)
		{
			status = "success";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public String isEmailAvailable(String email) 
	{
		//System.out.println("Email = "+regRepo.findByEmail(email));
		if(regRepo.findByEmail(email) == null)
		{
			status = "success";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public String isMobileAvailable(String mobile) 
	{
		//System.out.println("Mobile = "+regRepo.findByMobileNumber(mobile));
		if(regRepo.findByMobileNumber(mobile) == null)
		{
			status = "success";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public String isPhoneNumberAvailable(String phoneNumber) 
	{
		if(regRepo.findByPhoneNumber(phoneNumber) == null)
		{
			status = "success";
		}
		else
		{
			status = "fail";
		}
		return status;
	}
	public static String randomString(int length) 
	{
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++)
	      sb.append(SOURCE.charAt(secureRnd.nextInt(SOURCE.length())));
	    return sb.toString();
	}
	public String getCurrentPass(String emailId, ModelMap map, HttpSession session)
	{
		ChangePassword changePass = changePassRepo.findByEmailId(emailId);
		return changePass.getCurrentPassword();
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
}
