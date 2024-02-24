<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div style="text-align: center; margin-top: 5%">
	<button style="background-image: linear-gradient(to right, purple, #4d2d70, #5d2e7c, purple); height: 30px;"><a href="lastRecord" style="text-decoration: none;color:white;"><b>View Last Data</b></a></button>
</div>
<c:if test="${isData eq 1 }">

	<hr>
	<div class="data">
		
	
		
			<b>Instance Code =</b> ${data.instance_Id }<br>
			<b>Sector Code =</b> ${data.sec_code }<br>
			<b>Ministry Code =</b> ${data.ministry_Code }<br>
			<b>Department Code =</b> ${data.dept_code}<br>
			<b>Project Code =</b> ${data.project_code }<br>
			<b>Frequency Id =</b> ${data.mode_frequency_id }<br>
			<b>Group Id =</b> ${data.data_Group_Id}<br>
			<b>Data Date =</b> ${data.datadt }<br>
			<b>From Duration of Data =</b> ${data.entrydt }<br>
			<b>To Duration of Data =</b> ${data.datadt}<br>
			<b>Level1 Code =</b> ${data.level1Code }<br>
			<b>Level2 Code =</b> ${data.level2Code }<br>
			<b>Level3 Code =</b> ${data.level3Code }<br>
			<b>Level4 Code =</b> ${data.level4Code }<br>
			<b>Level5 Code =</b> ${data.level5Code }<br>
			<b>Level6 Code =</b> ${data.level6Code }<br>
			<b>Level7 Code =</b> ${data.level7Code }<br>
			<b>Level8 Code =</b> ${data.level8Code }<br>
			<b>Level9 Code =</b> ${data.level9Code }<br>
			<b>Level10 Code =</b> ${data.level10Code }<br>
			<b>KPI ID =</b> ${data.kpi_id }<br>
			<b>KPI Value =</b> ${data.kpi_Data }<br>
			
			
			<hr>
</c:if>	
</div>