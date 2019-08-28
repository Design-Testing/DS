<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<img src="${configurationParameters.banner}" alt="<p><jstl:out value ="${configurationParameters.banner}"/></p>" width="220" height="135"/>
	<br>
	<acme:display code="configurationParameters.sysName" value="${configurationParameters.sysName}" />
	<acme:display code="configurationParameters.welcomeMessageEsp" value="${configurationParameters.welcomeMessageEsp}" /><acme:display code="configurationParameters.welcomeMessageEn" value="${configurationParameters.welcomeMessageEn}" />
	<acme:display code="configurationParameters.countryPhoneCode" value="${configurationParameters.countryPhoneCode}" />
	<spring:message code="configurationParameters.creditCardMake" />
	<ul>
	<jstl:forEach items="${configurationParameters.creditCardMake}" var="make">
    			<li><jstl:out value="${make}"/></li>
    	</jstl:forEach>
    </ul>
    <dt><spring:message code="configurationParameters.voidWords" />:</dt>
	<dd>
	<div style="height:240px;width:120px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
		<jstl:forEach items="${configurationParameters.voidWords}" var="voidWord">
    				<li><jstl:out value="${voidWord}"/></li>
    	</jstl:forEach>
	</div>
	</dd>

	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="configurationParameters.edit" />"
           onclick="relativeRedir('configurationParameters/administrator/edit.do');" />