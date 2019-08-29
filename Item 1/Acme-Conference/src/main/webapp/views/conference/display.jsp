<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="services.ReviewerService"%>
<%@page import="domain.Reviewer"%>
<%@page import="java.util.Collection"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>




<!-------------------------------------------------------------------------------------------->
<!------------------------------- CREATE SUBMISSION BUTTON ----------------------------------->
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
	
	<jstl:if test="${ratioDecidedAssignedSubmissions == 1.0 }">
    	<h4 style="color: blue;"><spring:message code="conferece.all.assigned.submissions.decided"/></h4>
    </jstl:if>
	
	<jstl:if test="${ratioDecidedAssignedSubmissions < 1.0 and  ratioDecidedAssignedSubmissions >= 0.0}">
    	<h4 style="color: blue;"><spring:message code="conferece.not.all.assigned.submissions.decided"/></h4>
    </jstl:if>
	
	<jstl:if test="${not empty submissions and conference.isDraft eq false  }" >
			<acme:button url="conference/administrator/runAssignation.do?conferenceId=${conference.id}" name="display" code="conference.run.assignation"/>
			
			<acme:button url="conference/administrator/decideOnConference.do?conferenceId=${conference.id}" name="display" code="conference.run.decision"/>

			<acme:button url="conference/administrator/notifyStatus.do?conferenceId=${conference.id}" name="display" code="conference.notify"/>
	</jstl:if>
	
	
	
	<jstl:if test="${not empty notificationMsg  }">
    	<h4 style="color: red;"><spring:message code="${notificationMsg}"/></h4>
    </jstl:if>
	

	<jstl:if test="${not empty message.success.assign.reviewer}">
    	<h4 style="color: green;"><spring:message code="message.success.assign.reviewer"/></h4>
	</jstl:if>
	
	<jstl:if test="${not empty message.notification.successful}">
    	<h4 style="color: green;"><spring:message code="message.notification.successful"/></h4>
	</jstl:if>
	
	<jstl:if test="${not empty message.make.desicion.successful}">
    	<h4 style="color: green;"><spring:message code="message.make.desicion.successful"/></h4>
	</jstl:if>
	
	


<!------------------------------- Under-Reviewed submissions ----------------------------------->

<h4><spring:message code="conference.submissions.under.reviewed" /></h4>



<display:table name="underReviewedSubmissions" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="ticker" titleKey="submission.ticker" />
	
	
	<display:column property="status" titleKey="submission.status" />
	
	
	

		
		<display:column>
			<acme:button url="submission/administrator/display.do?submissionId=${row.id}" name="display" code="submission.display"/>
			
		</display:column>
		<display:column>
			<acme:button url="submission/administrator/assign.do?submissionId=${row.id}" name="display" code="submission.assign.reviewer"/>
		</display:column>
		
		<display:column>
			<acme:button url="submission/administrator/listAssignedReviewers.do?submissionId=${row.id}" name="display" code="submission.lst.assigned.reviewer"/>
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
			<acme:button url="submission/administrator/listAssignedReviewers.do?submissionId=${row.id}" name="display" code="submission.lst.assigned.reviewer"/>
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
			<acme:button url="submission/administrator/listAssignedReviewers.do?submissionId=${row.id}" name="display" code="submission.lst.assigned.reviewer"/>
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
<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${conference.isDraft}">
		<acme:button
			url="tutorial/create.do?conferenceId=${conference.id}&fromConferenceDisplay=fromConferenceDisplay"
			name="edit" code="activity.create" /><br/><br/>
	</jstl:if>
</security:authorize>
<acme:button url="panel/list.do?conferenceId=${conference.id}&fromConferenceDisplay=fromConferenceDisplay" name="panels" code="conference.panels" />
<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${conference.isDraft}">
		<acme:button
			url="panel/create.do?conferenceId=${conference.id}"
			name="edit" code="activity.create" /><br/><br/>
	</jstl:if>
</security:authorize>
<acme:button url="presentation/list.do?conferenceId=${conference.id}" name="presentations" code="conference.presentations" />
<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${conference.isDraft}">
		<acme:button
			url="presentation/create.do?conferenceId=${conference.id}&fromConferenceDisplay=fromConferenceDisplay"
			name="edit" code="activity.create" /><br/><br/>
	</jstl:if>
</security:authorize>
<br/><br/>
<jstl:choose>
	<jstl:when test="${isAdministrator eq true }">
		<acme:button url="conference/administrator/myConferences.do" name="back" code="conference.back" />
	</jstl:when>
	<jstl:otherwise>
		<acme:button url="conference/list.do" name="back" code="conference.back" />
	</jstl:otherwise>
</jstl:choose>




