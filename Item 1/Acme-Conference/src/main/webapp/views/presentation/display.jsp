<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<acme:showActivity title="${presentation.title}"
	attachments="${presentation.attachments}" hours="${presentation.hours}"
	minutes="${presentation.minutes}" room="${presentation.minutes}"
	speakers="${presentation.speakers}" summary="${presentation.summary}"
	lang="${lang}" />

<jstl:choose>
	<jstl:when test="${not empty presentation.startMoment}">
		<jstl:choose>
			<jstl:when test="${lang eq 'en' }">
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${presentation.startMoment}" type="both" pattern="yyyy/MM/dd HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${presentation.startMoment}" type="both" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
		<br />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.startMoment" />
		<spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>

	<acme:display code="presentation.camera.ready.paper" value="${presentation.cameraReadyPaper.title}"/><br/>
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${isDraft}">
	<acme:button url="presentation/delete.do?presentationId=${tutorial.id}&conferenceId=${conferenceId}"
		name="edit" code="activity.delete" /><br/>
	</jstl:if>
	</security:authorize>
	<acme:button url="presentation/list.do?conferenceId=${conferenceId}" name="back" code="activity.back" />