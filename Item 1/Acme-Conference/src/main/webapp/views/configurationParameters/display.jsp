<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<acme:display code="configurationParameters.banner" value="${configurationParameters.banner}" />
	<acme:display code="configurationParameters.sysName" value="${configurationParameters.sysName}" />
	<acme:display code="configurationParameters.welcomeMessageEsp" value="${configurationParameters.welcomeMessageEsp}" /><acme:display code="configurationParameters.welcomeMessageEn" value="${configurationParameters.welcomeMessageEn}" />
	<acme:display code="configurationParameters.countryPhoneCode" value="${configurationParameters.countryPhoneCode}" />
	<acme:display code="configurationParameters.creditCardMake" value="${configurationParameters.creditCardMake}" />
	<acme:display code="configurationParameters.voidWords" value="${configurationParameters.voidWords}" />

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="configurationParameters.edit" />"
           onclick="relativeRedir('configurationParameters/administrator/edit.do');"/>