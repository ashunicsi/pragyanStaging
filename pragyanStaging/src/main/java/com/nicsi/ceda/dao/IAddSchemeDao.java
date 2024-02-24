package com.nicsi.ceda.dao;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component(value = "iAddSchemeDao")
public interface IAddSchemeDao 
{
	String addScheme(ModelMap map);
}
