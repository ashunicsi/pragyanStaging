<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>

<%
	String sessionid = request.getSession().getId();
	response.setHeader("Set-Cookie", "JSESSIONID=" + sessionid + "; HttpOnly=true; SameSite=strict; secure=true");
	response.addHeader("Content-Security-Policy", "default-src 'self'  'unsafe-inline' https://cdnjs.cloudflare.com");
	response.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload"); 
%>
<link rel="stylesheet" href="assets/css/page.css">
<section id="skip_to_main">
	<div class="container-fluid">
   		<div class="head_content">
      		<a href="home"><img src="assets/images/Home_icon.svg" width="20px"></a> / <span>RTI</span>
  		</div>
	</div>
</section>
<section id="about_nicsi" class="nicsi__table text-start">
	<div class="container-fluid my_container">
      	<div class="row">
     		<div class="sc_head">
             	<h2 class="heading text-center">RTI</h2>
         		<div class="heading_division_white mb-5"></div>
           	</div>
   		</div>
   		<div class="row">
        	<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 col-xxl-12">
              	<ul class="nav nav-tabs RTI_tab" role="tablist">
              		<c:forEach items="${rtiMenu}" var="obj">
	              		<li class="nav-item" role="presentation">
	                    	<a class="nav-link ${obj.isActive }" id="${obj.menuId}" data-toggle="tab" href="${obj.url}"
	                                role="tab" aria-controls="${obj.ariaControls}" aria-selected="true">${obj.menuName}</a>
	                   </li>
                   </c:forEach>
                  
               </ul>
			</div>
			<div class="col-12 col-sm-12 col-md-12 col-lg-12 col-xl-12 col-xxl-12 mb-4">
            	<div class="tab-content mb-3">
            		<div class="tab-pane fade show active" role="tabpanel" aria-labelledby="RTI_Act_2005-tab" id="RTI_Act_2005">
                            
                        </div>
                        
                        
                        <jsp:useBean id="pagedListHolderSchemeDetails" scope="request"
											type="org.springframework.beans.support.PagedListHolder" />
										<c:url value="${page }" var="pagedLink">
											<c:param name="p" value="~" />
										</c:url>
                         <div class="tab-pane fade" id="RTI_Details" role="tabpanel" aria-labelledby="RTI_Details-tab">
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th class="sr_wid">Sr.No.</th>
                                            <th>RTI No.</th>
                                            <th>Applicant's Name</th>
                                            <th>Received Source</th>
                                            <th>Date of Receipt</th>
                                            <th>Current Status of RTI Request</th>
                                            <th>Closing Date</th>
                                        </tr>
                                    </thead>
                                    <tbody><%	int i =Integer.parseInt(session.getAttribute("j").toString());
                                				i++;
                                			%>
                                    <c:forEach items="${pagedListHolderSchemeDetails.pageList}" var="obj">
										
	                                        <tr>
	                                            <td><%out.print(i++); %></td>
	                                          
	                                        </tr>
	                                    
	                                </c:forEach>
                                    </tbody>
                                </table>
                                </div>
                               
                                <b>Total RTI  Records (${fn:length(rtiDetails)})</b><br>
                               		
										 <center>
									<tg:paging pagedListHolder="${pagedListHolderSchemeDetails}" pagedLink="${pagedLink}" />
								</center>
									
                        
                        
                     </div>
                     
        </div>
    </section>

    <!-- PO Details Modal -->
   
 <script>
        $(document).ready(function () 
        {
			//hides dropdown content
            $(".PO_details_change").hide();

            //unhides first option content
            $("#option18").show();

            //listen to dropdown for change
            $("#RTI_year_select").change(function () 
            {
                //rehide content on change
                $('.PO_details_change').hide();
                //unhides current item
                $('#' + $(this).val()).show();
            });

        });
    </script>