<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<acme:showActivity title="${panel.title}"
	attachments="${panel.attachments}" hours="${panel.hours}"
	minutes="${panel.minutes}" room="${panel.minutes}"
	speakers="${panel.speakers}" summary="${panel.summary}"
	lang="${lang}" />

<jstl:choose>
	<jstl:when test="${not empty panel.startMoment}">
		<jstl:choose>
			<jstl:when test="${lang eq 'en' }">
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${panel.startMoment}" type="both" pattern="yyyy/MM/dd HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${panel.startMoment}" type="both" pattern="dd/MM/yyyy HH:mm" />
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

	<acme:button url="panel/list.do?conferenceId=${conferenceId}" name="back" code="activity.back" />




