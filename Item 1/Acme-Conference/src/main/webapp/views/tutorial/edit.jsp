<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="tutorial/edit.do" modelAttribute="tutorial">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    
    
    <acme:textbox path="title" code="activity.title"/>
    <br/>
    
    <acme:textbox path="startMoment" code="activity.startMoment"/>
    <br/>
    
    <acme:textbox path="room" code="activity.room"/>
    <br/>
    
    <acme:textarea path="attachments" code="activity.attachments"/>
    <br/>

    <acme:numberbox path="hours" code="activity.hours" min="0"/>
    <br/>
    
    <acme:numberbox path="minutes" code="activity.minutes" min="0"/>
    <br/>
    
    <acme:textarea path="summary" code="activity.summary"/>
    <br/>
    
    <form:select path="speakers" code="activity.speakers">
    	<jstl:forEach items="${actors}" var="r">
    		<form:option value="${r.id}" label="${r.surname}-${r.name}"/>
    	</jstl:forEach>
    </form:select>
    <br/>

    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button">
        <spring:message code="activity.save"/>
    </button>

    <acme:button url="tutorial/list.do" name="back" code="activity.back"/>

</form:form>
