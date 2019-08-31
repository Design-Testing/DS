<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="quolet/company/edit.do" modelAttribute="netcache" method="POST">

	<form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="ticker"/>
    <form:hidden path="moment"/>
    <form:hidden path="isDraft" value="true"/>
    <form:hidden path="audit"/>
   	<input type="hidden" name="auditId" value="${auditId}"/>


    <acme:textbox path="body" code="quolet.body"/>
    <acme:textbox path="picture" code="quolet.picture"/>

    <br/>

    <!---------------------------- BOTONES -------------------------->

 	<acme:submit name="save" code="general.save"/>
 	
 	<acme:button url="audit/auditor/display.do?auditId=${auditId}" name="back" code="quolet.back"/>
 	



</form:form>