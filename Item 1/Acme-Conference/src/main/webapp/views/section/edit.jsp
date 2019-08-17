<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="section/edit.do" modelAttribute="section">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    
    <acme:textbox path="title" code="section.title"/>
    <br/>
    
    <acme:textarea path="summary" code="section.summary"/>
    <br/>

    <acme:textbox path="picture" code="section.picture"/>
    <br/>
    <br/>

    <!---------------------------- BOTONES -------------------------->
    <button name="save" type="submit" class="button">
        <spring:message code="section.save"/>
    </button>

	<acme:button url="section/list.do?tutorialId=${tutorialId}" name="back" code="section.back"/>

</form:form>
