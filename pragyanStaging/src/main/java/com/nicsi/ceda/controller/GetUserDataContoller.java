package com.nicsi.ceda.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nicsi.ceda.model.TableDataTemp;
import com.nicsi.ceda.repository.TableDataTempRepo;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class GetUserDataContoller 
{
	@Autowired
	private TableDataTempRepo tempRepo;
	
	@PostMapping(value="recvUserApiData1111", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> recvApidata(@RequestBody  List<TableDataTemp> table) throws JsonProcessingException, ParseException
	{
		
		tempRepo.saveAll(table);
		return null;
	}
	
}
