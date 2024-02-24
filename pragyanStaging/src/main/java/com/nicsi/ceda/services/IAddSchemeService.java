package com.nicsi.ceda.services;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component(value = "iAddSchemeService")
public interface IAddSchemeService 
{
	String addScheme(ModelMap map);
}
