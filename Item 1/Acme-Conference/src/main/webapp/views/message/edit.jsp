<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="message/edit.do" modelAttribute="message">

 	<form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="moment"/>
    <form:hidden path="sender"/>
    
	<form:label path="recivers">
        <spring:message code="message.recivers"/>:
    </form:label>
    <form:select path="recivers" code="message.recivers">
    	<jstl:forEach items="${recivers}" var="r">
    		<form:option value="${r.id}" label="${r.name}"/>
    	</jstl:forEach>
    </form:select>
    <br/>
    <br/>

    <acme:textbox path="subject" code="message.subject"/>
    <br/>

    <acme:textarea path="body" code="message.body"/>
    <br/>


    <form:label path="topic">
        <spring:message code="message.topic"/>:
    </form:label>
    <form:select path="topic" code="message.topic">
        <form:options items="${topics}"/>
    </form:select>
    <br/>
    <br/>
   
    <button name="save" type="submit" class="button2">
        <spring:message code="message.send"/>
    </button>

</form:form>
