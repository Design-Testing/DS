<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!-------------------------------------------------------------------------------------------->
<!------------------------------- CREATE CONFERENCE BUTTON ----------------------------------->
<!-------------------------------------------------------------------------------------------->

<jstl:if test="${isAuthor eq true and conference.isDraft eq false  }" >
			<jstl:if test="${availableToSubmit eq true }" >
			<acme:button url="submission/author/create.do?conferenceId=${conference.id}" name="display" code="conference.submits"/>
			</jstl:if>
			<jstl:if test="${availableToSubmit eq false }" >
			<h5 style="color: red;"><spring:message code="conference.submission.elapsed"/></h5>
			</jstl:if>

</jstl:if>

<!--------------------------------------------------------------------------------------->
<!------------------------------- GENERAL INFORMATION ----------------------------------->
<!--------------------------------------------------------------------------------------->


<h3><spring:message code="conference.general.information" /></h3>

<acme:display code="conference.title" value="${conference.title}" />

<acme:display code="conference.acronym" value="${conference.acronym}" />

<acme:display code="conference.venue" value="${conference.venue}" />

<acme:display code="conference.summary" value="${conference.summary}" />

<acme:display code="conference.fee" value="${conference.fee}" />



<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<acme:display code="conference.category" value="${conference.category.titleEn}" />
	</jstl:when>
	<jstl:otherwise>
		<acme:display code="conference.category" value="${conference.category.titleEs}" />
	</jstl:otherwise>
</jstl:choose>

<br>

<!----------------------------------------------------------------------------->
<!------------------------------- DEADLINES ----------------------------------->
<!----------------------------------------------------------------------------->

<h3><spring:message code="conference.deadlines" /></h3>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="conference.submission" />: <fmt:formatDate value="${conference.submission}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="conference.submission" />: <fmt:formatDate value="${conference.submission}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>

<br>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="conference.notification" />: <fmt:formatDate value="${conference.notification}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="conference.notification" />: <fmt:formatDate value="${conference.notification}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>

<br>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="conference.cameraReady" />: <fmt:formatDate value="${conference.cameraReady}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="conference.cameraReady" />: <fmt:formatDate value="${conference.cameraReady}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>

<br>

<!------------------------------------------------------------------------->
<!------------------------------- DATES ----------------------------------->
<!------------------------------------------------------------------------->

<h3><spring:message code="conference.dates" /></h3>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="conference.startDate" />: 
		<fmt:formatDate value="${conference.startDate}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="conference.startDate" />: 
		<fmt:formatDate value="${conference.startDate}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>

<br>

<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="conference.endDate" />: <fmt:formatDate value="${conference.endDate}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="conference.endDate" />: <fmt:formatDate value="${conference.endDate}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>


<br>
<br>
<br>


<!------------------------------------------------------------------------------->
<!------------------------------- SUBMISSIONS ----------------------------------->
<!------------------------------------------------------------------------------->

<jstl:if test="${isAdministrator eq true  }" >

	<h3><spring:message code="conference.submissions" /></h3>
	

	
	<jstl:if test="${not empty submissions and conference.isDraft eq false  }" >
			<acme:button url="conference/administrator/decideOnConference.do?conferenceId=${conference.id}" name="display" code="conference.run.decision"/>

	</jstl:if>
	
	
	


<!------------------------------- Under-Reviewed submissions ----------------------------------->

<h4><spring:message code="conference.submissions.under.reviewed" /></h4>

<jstl:if test="${not empty message.success.assign.reviewer}">
    	<h5 style="color: red;"><spring:message code="message.success.assign.reviewer"/></h5>
</jstl:if>

<display:table name="underReviewedSubmissions" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="ticker" titleKey="submission.ticker" />
	
	
	<display:column property="status" titleKey="submission.status" />
	
	
	

		
		<display:column>
			<acme:button url="submission/administrator/display.do?submissionId=${row.id}" name="display" code="submission.display"/>
			
		</display:column>
		<display:column>
			<jstl:if test="${row.status eq 'UNDER-REVIEWED' }" >
			<acme:button url="submission/administrator/assign.do?submissionId=${row.id}" name="display" code="submission.assign.reviewer"/>
			</jstl:if>
		</display:column>
	

	
	
	

</display:table>
<!------------------------------- Accepted submissions ----------------------------------->

<h4><spring:message code="conference.submissions.accepted" /></h4>

<display:table name="acceptedSubmissions" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="ticker" titleKey="submission.ticker" />
	
	
	<display:column property="status" titleKey="submission.status" />
	
	
	

		
		<display:column>
			<acme:button url="submission/administrator/display.do?submissionId=${row.id}" name="display" code="submission.display"/>
			
		</display:column>
		<display:column>
			<jstl:if test="${row.status eq 'UNDER-REVIEWED' }" >
			<acme:button url="submission/administrator/assign.do?submissionId=${row.id}" name="display" code="submission.assign.reviewer"/>
			</jstl:if>
		</display:column>
	

	
	
	

</display:table>
<!------------------------------- Rejected submissions ----------------------------------->

<h4><spring:message code="conference.submissions.rejected" /></h4>

<display:table name="rejectedSubmissions" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="ticker" titleKey="submission.ticker" />
	
	
	<display:column property="status" titleKey="submission.status" />
	
	
	

		
		<display:column>
			<acme:button url="submission/administrator/display.do?submissionId=${row.id}" name="display" code="submission.display"/>
			
		</display:column>
		<display:column>
			<jstl:if test="${row.status eq 'UNDER-REVIEWED' }" >
			<acme:button url="submission/administrator/assign.do?submissionId=${row.id}" name="display" code="submission.assign.reviewer"/>
			</jstl:if>
		</display:column>
	

	
	
	

</display:table>

</jstl:if>


<br>
<br>
<br>


<!-------------------------------------------------------------------------------->
<!------------------------------- BACK BUTTONS ----------------------------------->
<!-------------------------------------------------------------------------------->

<acme:button url="tutorial/list.do?conferenceId=${conference.id}" name="tutorials" code="conference.tutorials" />
<acme:button url="panel/list.do?conferenceId=${conference.id}" name="panels" code="conference.panels" />
<acme:button url="presentation/list.do?conferenceId=${conference.id}" name="presentations" code="conference.presentations" />
<br/><br/>
<jstl:choose>
	<jstl:when test="${isAdministrator eq true }">
		<acme:button url="conference/administrator/myConferences.do" name="back" code="conference.back" />
	</jstl:when>
	<jstl:otherwise>
		<acme:button url="conference/list.do" name="back" code="conference.back" />
	</jstl:otherwise>
</jstl:choose>




