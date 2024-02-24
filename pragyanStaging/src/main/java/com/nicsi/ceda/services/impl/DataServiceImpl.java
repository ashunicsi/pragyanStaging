package com.nicsi.ceda.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.dao.IDataDao;
import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.services.IDataService;
@Service
public class DataServiceImpl implements IDataService
{
	@Autowired
	private IDataDao dataDao;
	
	public List<SchemeDetailsDTO> findAllScheme(int status, ModelMap map, HttpServletRequest request, HttpSession session) 
	{
		return dataDao.findAllScheme(status, map, request, session);
	}
	public List<KPIDetailsDTO> findAllKPI(int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		return dataDao.findAllKPI(status, map, request, session);
	}

	public SchemeDetailsDTO findById(int id, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		return dataDao.findById(id, map, request, session);
	}

	public List<SchemeDetailsDTO> findAllSchemePagination(int pageNo, HttpServletRequest request, ModelMap map, HttpSession session) {
		return dataDao.findAllSchemePagination(pageNo, map, request, session);
	}
	
	public List<SchemeDetailsDTO> findByUsernameAndStatus(String username, int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		return dataDao.findByUsernameAndStatus(username, status, map, request, session);
	}
	public List<KPIDetailsDTO> findKpiByUsernameAndStatus(String username, int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		return dataDao.findKpiByUsernameAndStatus(username, status, map, request, session);
	}
	public List<SchemeDetailsDTO> findByUsernameAndIsRejected(String username, int status)
	{
		return dataDao.findByUsernameAndIsRejected(username, status);
	}
	public List<SchemeDetailsDTO> findByUsernameAndProject_Approval_Status(String username, int status, ModelMap map,
			HttpServletRequest request, HttpSession sessions)
	{
		return dataDao.findByUsernameAndProject_Approval_Status(username, status, map, request,sessions);
	}
	public List<SchemeDetailsDTO> findByProject_Approval_Status(int status, ModelMap map,
			HttpServletRequest request, HttpSession sessions)
	{
		return dataDao.findByProject_Approval_Status(status, map, request,sessions);
	}
	public List<SchemeDetailsDTO> findByIsRejected(int status)
	{
		return dataDao.findByIsRejected(status);
	}
	public List<SchemeDetailsDTO> findByProject_Approval_StatusAndIsRejected(int i, int j, ModelMap map,
			HttpServletRequest request, HttpSession session)
	{
		return dataDao.findByProject_Approval_StatusAndIsRejected(i, j, map, request, session);
	}

}
