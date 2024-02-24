package com.nicsi.ceda.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.IAddSchemeDao;
import com.nicsi.ceda.services.IAddSchemeService;

@Service
public class AddSchemeServiceImpl implements IAddSchemeService
{

	@Autowired
	private IAddSchemeDao dao;
	
	public String addScheme(ModelMap map) 
	{

		return dao.addScheme(map);
	}

}
