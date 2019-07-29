<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>







<form:form action="conference/administrator/edit.do" modelAttribute="conferenceForm">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    
    
    <acme:textbox path="title" code="conference.title"/>
    <br/>
    
    <acme:textbox path="acronym" code="conference.acronym"/>
    <br/>

    <acme:textbox path="venue" code="conference.venue"/>
    <br/>
    
    <acme:textarea path="summary" code="conference.summary"/>
    <br/>
    
    <acme:textbox path="fee" code="conference.fee"/>
    <br/>
    <br/>
    
    
     <jstl:if test="${alert}">
		<h5 style="color: red;"><spring:message code="conference.alert.dates"/></h5>
	</jstl:if>
	
	<acme:textbox path="submission" code="conference.submission"/>
	
	<acme:textbox path="notification" code="conference.notification"/>
	
	<acme:textbox path="cameraReady" code="conference.cameraReady"/>
	
	<acme:textbox path="startDate" code="conference.startDate"/>
	
	<acme:textbox path="endDate" code="conference.endDate"/>
	
	
	
	
    
    
    <form:label path="category">
        <spring:message code="conference.category"/>:
    </form:label>
    <form:select path="category" code="conference.category">
    	<jstl:forEach items="${categories}" var="cat">
    		<jstl:if test="${lang eq 'en' }" >
    			<form:option value="${cat.id}" label="${cat.titleEn}"/>
    		</jstl:if>
    		<jstl:if test="${lang eq 'es' }" >
    			<form:option value="${cat.id}" label="${cat.titleEs}"/>
    		</jstl:if>
    	</jstl:forEach>
    </form:select>
	
    <br/>
    <br/>

    



	<!--<form:label path="recipients">
        <spring:message code="message.recipients"/>:
    </form:label>
    <form:select path="recipients" code="message.recipients">
    	<jstl:forEach items="${recipients}" var="r">
    		<form:option value="${r.id}" label="${r.name}"/>
    	</jstl:forEach>
    </form:select> -->

    <!--<form:label path="priority">
        <spring:message code="message.priority"/>:
    </form:label>
    <form:select path="priority" code="message.priority">
        <form:options items="${priorities}"/>
    </form:select>-->
    <br/>
    <br/>


    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button2">
        <spring:message code="conference.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="conference.cancel" />"
           onclick="relativeRedir('conference/administrator/myConferences.do');"/>

</form:form>
