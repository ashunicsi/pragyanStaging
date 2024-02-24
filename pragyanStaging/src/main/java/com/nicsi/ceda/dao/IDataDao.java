package com.nicsi.ceda.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
@Component(value = "iDataDao")
public interface IDataDao 
{
	List<SchemeDetailsDTO> findAllScheme(int status, ModelMap map, HttpServletRequest request, HttpSession sessions);
	List<KPIDetailsDTO> findAllKPI(int status, ModelMap map, HttpServletRequest request, HttpSession session);

	SchemeDetailsDTO findById(int id, ModelMap map, HttpServletRequest request, HttpSession session);

	List<SchemeDetailsDTO> findAllSchemePagination(int pageNo, ModelMap map, HttpServletRequest request,
			HttpSession session);

	List<SchemeDetailsDTO> findByUsernameAndStatus(String usrename, int status, ModelMap map, HttpServletRequest request, HttpSession session);
	List<KPIDetailsDTO> findKpiByUsernameAndStatus(String username, int status, ModelMap map, HttpServletRequest request, HttpSession session);
	List<SchemeDetailsDTO> findByUsernameAndIsRejected(String username, int status);
	List<SchemeDetailsDTO> findByUsernameAndProject_Approval_Status(String username, int status, ModelMap map,
			HttpServletRequest request, HttpSession sessions);
	List<SchemeDetailsDTO> findByProject_Approval_Status(int status, ModelMap map, HttpServletRequest request,
			HttpSession sessions);
	List<SchemeDetailsDTO> findByIsRejected(int status);
	List<SchemeDetailsDTO> findByProject_Approval_StatusAndIsRejected(int i, int j, ModelMap map,
			HttpServletRequest request, HttpSession session);

}
