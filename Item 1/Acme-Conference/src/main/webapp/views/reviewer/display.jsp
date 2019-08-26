<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



	<acme:display code="reviewer.userAccount.username" value="${reviewer.userAccount.username}" />
	<acme:display code="reviewer.name" value="${reviewer.name}" />
	<acme:display code="reviewer.middleName" value="${reviewer.middleName}" />
	<acme:display code="reviewer.surname" value="${reviewer.surname}" />
	<acme:display code="reviewer.photo" value="${reviewer.photo}" />
	<acme:display code="reviewer.email" value="${reviewer.email}" />
	<acme:display code="reviewer.phone" value="${reviewer.phone}" />
	<acme:display code="reviewer.keywords" value="${reviewer.keywords}" />
	<acme:display code="reviewer.address" value="${reviewer.address}" />
	<acme:display code="reviewer.score" value="${reviewer.score}" />

	
	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="reviewer.edit" />"
           onclick="relativeRedir('reviewer/edit.do')"/>
           
<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="reviewer.back" />"
           onclick="relativeRedir('');"/>