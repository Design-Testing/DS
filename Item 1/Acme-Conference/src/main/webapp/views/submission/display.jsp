<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3><spring:message code="submission.general.information" /></h3>

<acme:display code="submission.ticker" value="${submission.ticker}" />

<acme:display code="submission.moment" value="${submission.moment}" />

<acme:display code="submission.status" value="${submission.status}" />

<acme:display code="submission.author" value="${submission.author.name}" />

<acme:display code="submission.conference" value="${submission.conference.title}" />

<br>
<br>

<h3><spring:message code="submission.review.paper" /></h3>

<acme:display code="submission.reviewPaper.title" value="${submission.reviewPaper.title}" />
<acme:display code="submission.reviewPaper.summary" value="${submission.reviewPaper.summary}" />
<acme:display code="submission.reviewPaper.document" value="${submission.reviewPaper.document}" />
<acme:display code="submission.reviewPaper.authors" value="${submission.reviewPaper.authors}" />


<br>
<br>

<h3><spring:message code="submission.camera.ready.paper" /></h3>

<jstl:if test="${not empty submission.cameraReadyPaper }">

<acme:display code="submission.cameraReadyPaper.title" value="${submission.cameraReadyPaper.title}" />
<acme:display code="submission.cameraReadyPaper.summary" value="${submission.cameraReadyPaper.summary}" />
<acme:display code="submission.cameraReadyPaper.document" value="${submission.cameraReadyPaper.document}" />
<acme:display code="submission.cameraReadyPaper.authors" value="${submission.cameraReadyPaper.authors}" />

<acme:button url="submission/author/editPaper.do?submissionId=${submission.id }&paperId=${submission.cameraReadyPaper.id }" name="back" code="submission.edit.paper" />
</jstl:if>

<jstl:if test="${empty submission.cameraReadyPaper }">

<acme:button url="submission/author/createPaper.do?submissionId=${submission.id }" name="back" code="submission.create.paper" />
</jstl:if>
<br>
<br>
<br>

<jstl:choose>
	<jstl:when test="${isAdministrator eq true }">
		<acme:button url="submission/administrator/submissions.do" name="back" code="submission.back" />
	</jstl:when>
	<jstl:when test="${isAuthor eq true }">
		<acme:button url="submission/author/mySubmissions.do" name="back" code="submission.back" />
	</jstl:when>
</jstl:choose>