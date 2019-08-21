<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="comment/edit.do" modelAttribute="comment">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="moment"/>
    <form:hidden path="author"/>
    <form:hidden path="conference"/>
    <form:hidden path="activity"/>
    <form:hidden path="report"/>
    <!-- TODO: sustuir Quolet por nombre de nueva entidad y añadir el bloque de codigo -->
    <!-- form:hidden path="quolet" --> 
    
    <acme:textbox path="title" code="comment.title"/>
    <acme:textarea path="text" code="comment.text"/>
    <br/>
    
    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button">
        <spring:message code="comment.save"/>
    </button>

</form:form>
