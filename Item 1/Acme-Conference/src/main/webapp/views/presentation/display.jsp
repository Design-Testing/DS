<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<acme:showActivity title="${presentation.title}"
	hours="${presentation.hours}"
	minutes="${presentation.minutes}" room="${presentation.minutes}"
	summary="${presentation.summary}"
	lang="${lang}" />

<jstl:choose>
	<jstl:when test="${not empty presentation.startMoment}">
		<jstl:choose>
			<jstl:when test="${lang eq 'en' }">
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${presentation.startMoment}" type="both"
					pattern="yyyy/MM/dd HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${presentation.startMoment}" type="both"
					pattern="dd/MM/yyyy HH:mm" />
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
<jstl:set value="${presentation.attachments.size()}" var="s1"/>
<jstl:set value="${presentation.speakers.size()}" var="s2"/>
<jstl:choose>
	<jstl:when
		test="${not empty presentation.attachments and (s1 gt 1)}">
		<spring:message code="activity.attachments" />
		<jstl:forEach items="${presentation.attachments}" var="line">
			<ul>
				<li><a href="${line}"><jstl:out value="${line}" /></a></li>
			</ul>
		</jstl:forEach>
	</jstl:when>
	<jstl:when
		test="${not empty presentation.attachments and (s1 le 1)}">
		<spring:message code="activity.attachments" />: 
			<a href="${presentation.attachments.get(0)}"><jstl:out value="${presentation.attachments.get(0)}" /></a>
		<br />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.attachments" />
		<spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>
<jstl:choose>
	<jstl:when
		test="${not empty presentation.speakers and (s2 gt 1)}">
		<spring:message code="activity.speakers" />
		<jstl:forEach items="${presentation.speakers}" var="line">
			<ul>
				<li><jstl:out value="${line}" /></li>
			</ul>
		</jstl:forEach>
	</jstl:when>
	<jstl:when
		test="${not empty presentation.speakers and (s2 le 1)}">
		<spring:message code="activity.speakers" />: 
			<jstl:out value="${presentation.speakers.get(0)}" />
		<br />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.speakers" />
		<spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>

<acme:display code="presentation.camera.ready.paper"
	value="${presentation.cameraReadyPaper.title}" />
<br />

<security:authorize access="hasRole('ADMIN')">
		<acme:button
			url="presentation/delete.do?presentationId=${presentation.id}&conferenceId=${conferenceId}"
			name="edit" code="activity.delete" />
		<br />
</security:authorize>
<acme:button url="presentation/list.do?conferenceId=${conferenceId}"
	name="back" code="activity.back" />
	
	<jstl:if test="${not empty error}">
	<h3 style="color: red;">
		<spring:message code="${error}" />
	</h3>
</jstl:if>
	