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

<form:form action="tutorial/edit.do?conferenceId=${conferenceId}" modelAttribute="tutorial">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="sections"/>
    
    
    <acme:textbox path="title" code="activity.title"/>
    <br/>
    
    <acme:textbox path="startMoment" code="activity.startMoment"/>
    <br/>
    
    <acme:numberbox path="hours" code="activity.hours" min="0"/>
    <br/>
    
    <acme:numberbox path="minutes" code="activity.minutes" min="0"/>
    <br/>
    
    <acme:textarea path="summary" code="activity.summary"/>
    <br/>
    
    <acme:textbox path="room" code="activity.room"/>
    <br/>
    
    <acme:textarea path="attachments" code="activity.attachments"/>
    <br/>
    
    <form:label path="speakers">
        <spring:message code="activity.speakers"/>:
    </form:label>
    <form:select path="speakers" code="activity.speakers">
    	<form:options items="${actors}" itemLabel="name"/>
    </form:select>
    <br/>

    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button">
        <spring:message code="activity.save"/>
    </button>

<jstl:choose>
<jstl:when test="${fromConferenceDisplay eq 'fromConferenceDisplay'}">
	<acme:button url="conference${autorize}/display.do?conferenceId=${conferenceId}" name="back" code="activity.back"/>
</jstl:when>
<jstl:otherwise>
	<acme:button url="tutorial/list.do?conferenceId=${conferenceId}" name="back" code="activity.back"/>
</jstl:otherwise>
</jstl:choose>    

</form:form>
