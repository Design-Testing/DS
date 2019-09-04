<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<acme:display code="comment.title" value="${comment.title}" />

<acme:display code="comment.text" value="${comment.text}" />

<acme:display code="comment.moment" value="${comment.moment}" />

<jstl:choose>
<jstl:when test="${not empty comment.author}">
	<acme:display code="comment.author" value="${comment.author}" />
</jstl:when>
<jstl:otherwise>
	<spring:message code="anonymus.comment"/>
</jstl:otherwise>
</jstl:choose>

<jstl:if test="${not empty comment.conference}">
	<acme:display code="comment.conference" value="${comment.conference.title}" url="conference/display.do?conferenceId=${comment.conference.id}"/>
</jstl:if>
<jstl:if test="${not empty comment.activity and (entity eq 'presentation')}">
	<acme:display code="comment.presentation" value="${comment.activity.title}" url="presentation/display.do?presentationId=${comment.activity.id}"/>
</jstl:if>
<jstl:if test="${not empty comment.activity and (entity eq 'panel')}">
	<acme:display code="comment.panel" value="${comment.activity.title}" url="panel/display.do?panelId=${comment.activity.id}"/>
</jstl:if>
<jstl:if test="${not empty comment.activity and (entity eq 'tutorial')}">
	<acme:display code="comment.tutorial" value="${comment.activity.title}" url="tutorial/display.do?tutorialId=${comment.activity.id}"/>
</jstl:if>
<jstl:if test="${not empty comment.report}">
	<acme:display code="comment.report" value="${comment.report.decision}"/>
</jstl:if>

<acme:button url="${lastURL}" name="back" code="comment.back" />




