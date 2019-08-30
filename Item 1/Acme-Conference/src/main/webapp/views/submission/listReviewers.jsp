<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<jstl:if test="${empty reviewers}">
<h3><spring:message code="submission.noReviewersKeywords" /></h3>
</jstl:if>

<jstl:if test="${not empty reviewers}">

<display:table name="reviewers" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="name" titleKey="submission.reviewer.name" />
	
	<display:column titleKey="submission.reviewer.keywords" >
					<jstl:forEach items="${row.keywords}" var="keyword">
					<jstl:out value="${keyword}" />, 
					</jstl:forEach>
    </display:column>
	

		<display:column>
			<acme:button url="submission/administrator/assignToReviewer.do?submissionId=${submissionId}&reviewerId=${row.id }" name="display" code="submission.assign.reviewer"/>
		</display:column>

	
	

</display:table>

</jstl:if>


<acme:button url="conference/administrator/display.do?conferenceId=${conferenceId }" name="back" code="submission.back" />