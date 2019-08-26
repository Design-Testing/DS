<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="reviewer/save.do" modelAttribute="reviewerForm">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="userAccountpassword"/>
    
    <acme:textbox path="userAccountuser" code="reviewer.userAccount.username"/>
    <br/>
    
    <acme:textbox path="name" code="reviewer.name"/>
    <br/>
    <acme:textbox path="surname" code="reviewer.surname"/>
    <br/>
    <acme:textbox path="middleName" code="reviewer.middleName"/>
    <br/>
    <acme:textbox path="photo" code="reviewer.photo"/>
    <br/>
    <acme:textbox path="email" code="reviewer.email"/>
    <br/>
    <acme:textbox path="phone" code="reviewer.phone"/>
    <br/>
    <acme:textbox path="address" code="reviewer.address"/>
    <br/>
    <acme:textbox path="keywords" code="reviewer.keywords"/>
    <br/>

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
        <spring:message code="reviewer.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancell"
           value="<spring:message code="reviewer.cancel" />"
           onclick="relativeRedir('reviewer/display.do');"/>

</form:form>
