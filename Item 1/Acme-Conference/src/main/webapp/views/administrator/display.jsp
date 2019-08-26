<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



	<acme:display code="administrator.userAccount.username" value="${administrator.userAccount.username}" />
	<acme:display code="administrator.name" value="${administrator.name}" />
	<acme:display code="administrator.middleName" value="${administrator.middleName}" />
	<acme:display code="administrator.surname" value="${administrator.surname}" />
	<acme:display code="administrator.photo" value="${administrator.photo}" />
	<acme:display code="administrator.email" value="${administrator.email}" />
	<acme:display code="administrator.phone" value="${administrator.phone}" />
	<acme:display code="administrator.address" value="${administrator.address}" />
	<acme:display code="administrator.score" value="${administrator.score}" />

	
	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="administrator.edit" />"
           onclick="relativeRedir('administrator/edit.do')"/>
           
<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="administrator.back" />"
           onclick="relativeRedir('');"/>