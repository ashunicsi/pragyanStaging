package com.nicsi.ceda.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nicsi.ceda.model.Login;
import com.nicsi.ceda.repository.LoginRepo;

@Component
public class UnlockAllUser 
{
	@Autowired
	private LoginRepo loginRepo;
	
	@Scheduled(cron = "0 0 0 * * ?") // Run at midnight
	public void unlockAllUser()
	{
		List<Login> log = loginRepo.findByIsLocked("Yes");
		for(Login l : log)
		{
			l.setIsLocked("No");
			l.setNoOfAttempts(0);
			loginRepo.save(l);
		}
	}
}
