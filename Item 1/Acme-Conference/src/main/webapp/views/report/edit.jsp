<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<security:authorize access="hasRole('ADMIN')">
	<jstl:set value="/administrator" var="autorize"/>
</security:authorize>

<form:form action="report/edit.do" modelAttribute="report">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="reviewer"/>
    <form:hidden path="submission"/>
    
    <acme:numberbox path="originality" code="report.originality" min="0"/>
    <br/>
    
    <acme:numberbox path="quality" code="report.quality" min="0"/>
    <br/>
    
    <acme:numberbox path="readability" code="report.readability" min="0"/>
    <br/>
    
	<label for="decision">
		<spring:message code="report.decision" />:
	</label>
	<select id="decision" name="decision">
		<option value="REJECT">REJECT</option>
		<option value="BORDER-LINE">BORDER-LINE</option>
		<option value="ACCEPT">ACCEPT</option>
	</select>
	<br>
	
	<form:label path="isDraft">
		<spring:message code="report.isDraft" />:
	</form:label>
    <form:checkbox path="isDraft"/>
    <br/>
    
    
    <button name="save" type="submit" class="button">
        <spring:message code="report.save"/>
    </button>
    



</form:form>

    <button class="button" onclick="location.href = 'comment/create.do?entity=report&entityId=${report.id}'">
        <spring:message code="report.addComment"/>
    </button>
    <button class="button" onclick="location.href = 'comment/list.do?entity=report&id=${report.id}'">
        <spring:message code="report.listComment"/>
    </button>
    <acme:button code="report.submission" name="submission" url="submission/reviewer/display.do?submissionId=${report.submission.id}"/>
