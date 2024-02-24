package com.nicsi.ceda.dao.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;

import com.nicsi.ceda.dao.IDataDao;
import com.nicsi.ceda.model.KPIDetailsDTO;
import com.nicsi.ceda.model.SchemeDetailsDTO;
import com.nicsi.ceda.model.Sector;
import com.nicsi.ceda.model.Tbl_Project_Detail_Intrim;
import com.nicsi.ceda.model.Tbl_Project_KPI_Detail_Intrim;
import com.nicsi.ceda.repository.MLevelRepo;
import com.nicsi.ceda.repository.SectorRepo;
import com.nicsi.ceda.repository.Tbl_Project_Detail_IntrimRepo;
import com.nicsi.ceda.repository.Tbl_Project_KPI_Detail_IntrimRepo;

@Repository
public class DataDaoImpl implements IDataDao
{
	
	@Autowired
	private Tbl_Project_Detail_IntrimRepo projRepo;
	@Autowired
	private MLevelRepo levelRepo;
	@Autowired
	private Tbl_Project_KPI_Detail_IntrimRepo kpiRepo;
	@Autowired
	private SectorRepo sectorRepo;
	
	public List<SchemeDetailsDTO> findAllScheme(int status, ModelMap map, HttpServletRequest request, HttpSession session) 
	{
		//int pageSize = 10;
		//int pageNo = 1;
		//Pageable pageable = PageRequest.of(pageNo, pageSize);
		//Page<Tbl_Project_Detail_Intrim> modelPage = projRepo.findAll(pageable);
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByStatus(status);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			Sector sector = sectorRepo.findBySectorId(proj.getSec_code());
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int sectorCode = proj.getSec_code();
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setSectorCode(sectorCode);
			dto.setSectorName(sector.getSector_Name_e());
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setIsRejected(proj.getIsRejected());
			schemeData.add(dto);
		}
		/*
		schemeData = schemeData.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getMinistryName)).collect(Collectors.toList());
		map.addAttribute("activeSchemeData", schemeData);
		PagedListHolder pagedListHolderSchemedata = new PagedListHolder(schemeData);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		map.put("pagedListHolderActiveSchemeDetails", pagedListHolderSchemedata);
		map.addAttribute("page", "/viewActiveSchemePagination");
		pagedListHolderSchemedata.setPage(page);
		pagedListHolderSchemedata.setPageSize(10);
		session.setAttribute("j", 0);
		schemeData = new ArrayList<SchemeDetailsDTO>();
		for(Tbl_Project_Detail_Intrim proj : deactiveSchemeDet)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_Code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getMinistry_Code(), 21);
			String schemeName = proj.getProject_Name_E();
				
			int ministryCode = proj.getMinistry_Code();
			int departmentCode = proj.getDept_Code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_Cord_Email();
			String headEmail = proj.getProject_Head_Email();
			String adminEmail = proj.getProject_Head_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			schemeData.add(dto);
		}
		schemeData = schemeData.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getMinistryName)).collect(Collectors.toList());
		map.addAttribute("deactiveSchemeData", schemeData);
		pagedListHolderSchemedata = new PagedListHolder(schemeData);
		page = ServletRequestUtils.getIntParameter(request, "p", 0);
		map.put("pagedListHolderDeativeSchemeDetails", pagedListHolderSchemedata);
		map.addAttribute("page", "/viewDeactiveSchemePagination");
		pagedListHolderSchemedata.setPage(page);
		pagedListHolderSchemedata.setPageSize(10);
		session.setAttribute("k", 0);
		*/
		return schemeData;
	}

	public List<SchemeDetailsDTO> findAllSchemePagination(int pageNo, ModelMap map, HttpServletRequest request, HttpSession session) 
	{
		List<Tbl_Project_Detail_Intrim> activeSchemeDet = projRepo.findByStatus(1);
		List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatusAndIsRejected(0, 0);
		
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		for(Tbl_Project_Detail_Intrim proj : activeSchemeDet)
		{
				String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
				String departmentName = levelRepo.getDepartmentName(proj.getMinistry_code(), 21);
				String schemeName = proj.getProject_name_e();
				
				int ministryCode = proj.getMinistry_code();
				int departmentCode = proj.getDept_code();
				int schemeCode = proj.getProjectCode();
				
				String cordEmail = proj.getProject_cord_email();
				String headEmail = proj.getProject_head_email();
				String adminEmail = proj.getProject_head_email();
				
				SchemeDetailsDTO dto = new SchemeDetailsDTO();
				dto.setAdminEmail(adminEmail);
				dto.setCordEmail(cordEmail);
				dto.setDepartmentCode(departmentCode);
				dto.setDepartmentName(departmentName);
				dto.setHeadEmail(headEmail);
				dto.setMinistryCode(ministryCode);
				dto.setMinistryName(ministryName);
				dto.setSchemeCode(schemeCode);
				dto.setSchemeName(schemeName);
				schemeData.add(dto);
			}
			
			
		
		
		schemeData = schemeData.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getMinistryName)).collect(Collectors.toList());
		map.addAttribute("activeSchemeData", schemeData);
		PagedListHolder pagedListHolderSchemedata = new PagedListHolder(schemeData);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		map.put("pagedListHolderActiveSchemeDetails", pagedListHolderSchemedata);
		map.addAttribute("page", "/viewActiveSchemePagination");
		pagedListHolderSchemedata.setPage(page);
		pagedListHolderSchemedata.setPageSize(10);
		
		
		if(pageNo  > schemeData.size()/10 )
		{
			session.setAttribute("j", schemeData.size()/10 + "0");
			
		}
		else
		{
			String count = pageNo+"0";
			session.setAttribute("j", count);
		}
		
		schemeData = new ArrayList<SchemeDetailsDTO>();
		for(Tbl_Project_Detail_Intrim proj : deactiveSchemeDet)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getMinistry_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_head_email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			schemeData.add(dto);
		}
		schemeData = schemeData.stream().sorted(Comparator.comparing(SchemeDetailsDTO::getMinistryName)).collect(Collectors.toList());
		map.addAttribute("deactiveSchemeData", schemeData);
		pagedListHolderSchemedata = new PagedListHolder(schemeData);
		page = ServletRequestUtils.getIntParameter(request, "p", 0);
		map.put("pagedListHolderDeativeSchemeDetails", pagedListHolderSchemedata);
		map.addAttribute("deactivePage", "/viewDeactiveSchemePagination");
		pagedListHolderSchemedata.setPage(page);
		pagedListHolderSchemedata.setPageSize(10);
		return schemeData;
	}
	public SchemeDetailsDTO findById(int id, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		Tbl_Project_Detail_Intrim schemeDetails = projRepo.findById(id);

		String ministryName = levelRepo.getMinistryName(schemeDetails.getMinistry_code(), 22);
		String departmentName = levelRepo.getDepartmentName(schemeDetails.getMinistry_code(), 21);
		String schemeName = schemeDetails.getProject_name_e();
			
		SchemeDetailsDTO dto = new SchemeDetailsDTO();
		dto.setAdminEmail("");
		dto.setCordEmail(schemeDetails.getProject_cord_email());
		dto.setDepartmentCode(schemeDetails.getDept_code());
		dto.setDepartmentName(departmentName);
		dto.setHeadEmail(schemeDetails.getProject_head_email());
		dto.setMinistryCode(schemeDetails.getMinistry_code());
		dto.setMinistryName(ministryName);
		dto.setSchemeCode(schemeDetails.getProjectCode());
		dto.setSchemeName(schemeName);
		 
		return dto;
	}
	public List<KPIDetailsDTO> findAllKPI(int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		
		List<Tbl_Project_KPI_Detail_Intrim> kpiDetails = kpiRepo.findByStatus(status);
		//System.out.println(kpiDetails);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<KPIDetailsDTO> schemeData = new ArrayList<KPIDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_KPI_Detail_Intrim kpi : kpiDetails)
		{
			
			Tbl_Project_Detail_Intrim scheme = projRepo.findByProjectCode(kpi.getProjectCode());
					
			String ministryName = levelRepo.getMinistryName(kpi.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(kpi.getDept_code(), 21);
			String schemeName = scheme.getProject_name_e();
			KPIDetailsDTO dto = new KPIDetailsDTO();
			dto.setMinistryCode(kpi.getMinistry_code());
			dto.setDeptCode(kpi.getDept_code());
			dto.setSchemeCode(kpi.getProjectCode());
			
			dto.setMinistryName(ministryName);
			dto.setDeptName(departmentName);
			dto.setSchemeName(schemeName);
			dto.setKpiName(kpi.getKpi_name_e());
			dto.setTooltip(kpi.getKpiTooltipR());
			dto.setKpi_data_display_type_name(kpi.getKpi_data_display_type_name());
			//System.out.println(dto);
			dto.setKpiId(kpi.getKpiId());
			schemeData.add(dto);
		}
		return schemeData;
	}
	public List<SchemeDetailsDTO> findByUsernameAndStatus(String usrename, int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByUsernameAndStatusAndIsRejectedAndProject_Approval_Status(usrename, status, 0, 1);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			Sector sector = sectorRepo.findBySectorId(proj.getSec_code());
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
			int sectorCode=proj.getSec_code();
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setSectorCode(sectorCode);
			dto.setSectorName(sector.getSector_Name_e());
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			
			schemeData.add(dto);
		}
		return schemeData;
	}
	
	public List<KPIDetailsDTO> findKpiByUsernameAndStatus(String username, int status, ModelMap map, HttpServletRequest request, HttpSession session)
	{
		
		List<Tbl_Project_KPI_Detail_Intrim> kpiDetails = kpiRepo.findByUsernameAndStatus(username, status);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<KPIDetailsDTO> schemeData = new ArrayList<KPIDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_KPI_Detail_Intrim kpi : kpiDetails)
		{
			Tbl_Project_Detail_Intrim scheme = projRepo.findByProjectCode(kpi.getProjectCode());
			if(scheme != null)
			{
				String ministryName = levelRepo.getMinistryName(kpi.getMinistry_code(), 22);
				String departmentName = levelRepo.getDepartmentName(kpi.getDept_code(), 21);
				
				String schemeName = scheme.getProject_name_e();
				KPIDetailsDTO dto = new KPIDetailsDTO();
				dto.setMinistryCode(kpi.getMinistry_code());
				dto.setDeptCode(kpi.getDept_code());
				dto.setSchemeCode(kpi.getProjectCode());
				
				dto.setMinistryName(ministryName);
				dto.setDeptName(departmentName);
				dto.setSchemeName(schemeName);
				dto.setKpiName(kpi.getKpi_name_e());
				dto.setTooltip(kpi.getKpiTooltipR());
				dto.setKpi_data_display_type_name(kpi.getKpi_data_display_type_name());
				//System.out.println(dto);
				
				dto.setKpiId(kpi.getKpiId());
				schemeData.add(dto);
			}
			
		}
		return schemeData;
	}
	public 	List<SchemeDetailsDTO> findByUsernameAndIsRejected(String username, int status)
	{
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByUsernameAndIsRejected(username, status);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setProjectRejectionDate(proj.getProjectRejectionDate());
			dto.setRejectMessage(proj.getRejectMessage());
			schemeData.add(dto);
		}
		return schemeData;
		
	}
	public List<SchemeDetailsDTO> findByUsernameAndProject_Approval_Status(String username, int status, ModelMap map,
			HttpServletRequest request, HttpSession sessions)
	{
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByUsernameAndProjectApprovalStatus(username, status);
		
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setEntryDate(proj.getEntry_Date());
			schemeData.add(dto);
		}
		return schemeData;
	}
	public List<SchemeDetailsDTO> findByProject_Approval_Status(int status, ModelMap map,
			HttpServletRequest request, HttpSession sessions)
	{
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByProject_Approval_Status(status);
		
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setEntryDate(proj.getEntry_Date());
			schemeData.add(dto);
		}
		return schemeData;
	}
	public List<SchemeDetailsDTO> findByIsRejected(int status)
	{
		List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByIsRejected(status);
		//List<Tbl_Project_Detail_Intrim> deactiveSchemeDet = projRepo.findByStatus(0);
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setProjectRejectionDate(proj.getProjectRejectionDate());
			dto.setRejectMessage(proj.getRejectMessage());
			schemeData.add(dto);
		}
		return schemeData;
	}
	public List<SchemeDetailsDTO> findByProject_Approval_StatusAndIsRejected(int status, int j, ModelMap map,
			HttpServletRequest request, HttpSession session)
	{
List<Tbl_Project_Detail_Intrim> schemeDetails = projRepo.findByProject_Approval_StatusAndIsRejected(status, j);
		
		List<SchemeDetailsDTO> schemeData = new ArrayList<SchemeDetailsDTO>();
		int i = 1;
		//modelPage.getContent()
		for(Tbl_Project_Detail_Intrim proj : schemeDetails)
		{
			
			String ministryName = levelRepo.getMinistryName(proj.getMinistry_code(), 22);
			String departmentName = levelRepo.getDepartmentName(proj.getDept_code(), 21);
			String schemeName = proj.getProject_name_e();
				
			int ministryCode = proj.getMinistry_code();
			int departmentCode = proj.getDept_code();
			int schemeCode = proj.getProjectCode();
				
			String cordEmail = proj.getProject_cord_email();
			String headEmail = proj.getProject_head_email();
			String adminEmail = proj.getProject_Admin_Email();
				
			SchemeDetailsDTO dto = new SchemeDetailsDTO();
			int id = proj.getId().intValue();
			dto.setId(id);
			dto.setAdminEmail(adminEmail);
			dto.setCordEmail(cordEmail);
			dto.setDepartmentCode(departmentCode);
			dto.setDepartmentName(departmentName);
			dto.setHeadEmail(headEmail);
			dto.setMinistryCode(ministryCode);
			dto.setMinistryName(ministryName);
			
			dto.setSchemeCode(schemeCode);
			dto.setSchemeName(schemeName);
			dto.setEntryDate(proj.getEntry_Date());
			schemeData.add(dto);
		}
		return schemeData;
	}
}
