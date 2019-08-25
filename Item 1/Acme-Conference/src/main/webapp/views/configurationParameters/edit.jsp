<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="configurationParameters/administrator/edit.do" modelAttribute="configurationParameters">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
     
    
    <acme:textbox path="banner" code="configurationParameters.banner"/>
    <br/>
    
    <acme:textbox path="countryPhoneCode" code="configurationParameters.countryPhoneCode"/>
    <br/>

    <acme:textbox path="sysName" code="configurationParameters.sysName"/>
    <br/>
    
    <acme:textarea path="welcomeMessageEsp" code="configurationParameters.welcomeMessageEsp"/>
    <br/>
    
    <acme:textbox path="welcomeMessageEn" code="configurationParameters.welcomeMessageEn"/>
    <br/>
    <acme:textbox path="creditCardMake" code="configurationParameters.creditCardMake"/>
    <br/>
    <acme:textbox path="voidWords" code="configurationParameters.voidWords"/>
    <br/>
    <br/>
    
    
	
	<br/>
    <br/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>


    <button name="save" type="submit" class="button2">
        <spring:message code="configurationParameters.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="configurationParameters.cancel" />"
           onclick="relativeRedir('configurationParameters/administrator/display.do');"/>

</form:form>
