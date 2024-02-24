package com.nicsi.ceda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nicsi.ceda.model.Registration;

public interface RegistrationRepo extends JpaRepository<Registration, Integer>
{
	Registration findByEmail(String email);
	Registration findByMobileNumber(String mobileNumber);
	Registration findByPhoneNumber(String phoneNumber);
	Registration findByTokenKey(String tokenKey);
}
