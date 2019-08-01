<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:out value="${lang }"/>


<form:form action="conference/administrator/edit.do" modelAttribute="conferenceForm">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    
    
    <h3><spring:message code="conference.general.information" /></h3>
    
    
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
	
	<br/>
	
	<h5 style="color: red;"><spring:message code="conference.alert.dates"/></h5>
	
	<br/>
	
	<h3><spring:message code="conference.deadlines" /></h3>
	
	<acme:textbox path="submission" code="conference.submission" placeholder="yyyy-MM-dd HH:mm"/>
	
	<br/>
	
	<acme:textbox path="notification" code="conference.notification" placeholder="yyyy-MM-dd HH:mm"/>
	
	<br/>
	
	<acme:textbox path="cameraReady" code="conference.cameraReady" placeholder="yyyy-MM-dd HH:mm"/>
	
	<br/>
	
	<h3><spring:message code="conference.dates" /></h3>
	
	<acme:textbox path="startDate" code="conference.startDate" placeholder="yyyy-MM-dd HH:mm" />
	
	<br/>
	
	<acme:textbox path="endDate" code="conference.endDate" placeholder="yyyy-MM-dd HH:mm"/>
	
	
	
	
      
	
    <br/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
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
