<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<html>
   <head>
      <title>JSTL sql:query Tag</title>
   </head>
   <body>
      <sql:setDataSource var = "snapshot" driver = "org.postgresql.Driver"
         url = "jdbc:postgresql://10.23.124.59:5432/cedadevtest"
         user = "postgres" password = "Cloud#$1110"/>
         <sql:query dataSource = "${snapshot}" var = "result">
       Select pd.project_code, pd.state_name,district_name,sum((indicator='Farmers Registered')::int) 
"Villages Verified as ODF",sum((indicator='Total Number of Villages')::int) 
"Total Number of Villages",sum((indicator='Village Declared as ODF')::int) 
"Village Declared as ODF",sum((indicator='District Declared as ODF')::int) 
"District Declared as ODF",sum((indicator='Toilets Constructed')::int) 
"Toilets Constructed"FROM pm_dashboard_new  as pd inner join KPI_Cumulative_Current k on
k.project_code = pd.project_code and k.Label = pd.label where pd.project_code= 1003   
GROUP BY pd.project_code, pd.state_name,Indicator,district_name;
      </sql:query>
      <table border = "1" width = "100%">
         <tr>
            <th>Serial Number</th>
            <th>Project Code</th>
            <th>Nation Name</th>
            <th>State Name</th>
            <th>District Name</th>
            
         </tr><%int i = 1; %>
         <c:forEach var = "row" items = "${result.rows}">
            <tr>
            	<td><%out.print(i++); %></td>
               	<td> <c:out value = "${row.project_code}"/></td>
                <td> <c:out value = "India"/></td>
                <td> <c:out value = "${row.state_name}"/></td>
                <td> <c:out value = "${row.district_name}"/></td>
                
               
            </tr>
         </c:forEach>
         <c:forEach var = "row" items = "${result.rows}">
        	<c:out value = "${row}"/>
         </c:forEach>
      </table>
   </body>
</html>