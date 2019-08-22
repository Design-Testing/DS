<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code='section.img' var="altern"/>
<acme:display code="section.title" value="${section.title}" />
<acme:display code="section.summary" value="${section.title}" />
<acme:img code="section.picture" url="${section.pictures}" alt="${altern}"/><br/><br/>

<security:authorize access="hasRole('ADMIN')">
	<acme:button url="section/edit.do?sectionId=${section.id}&tutorialId=${tutorialId}" name="edit" code="section.edit" />
</security:authorize>

<acme:button url="section/list.do?tutorialId=${tutorialId}&conferenceId=${conferenceId}" name="back" code="section.back" />
<acme:button url="tutorial/display.do?tutorialId=${tutorialId}&conferenceId=${conferenceId}" name="tutorial" code="section.tutorial" />