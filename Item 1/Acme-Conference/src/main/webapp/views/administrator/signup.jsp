<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="administrator/save.do" modelAttribute="actorForm">
     
    
    <form:hidden path="id"/>
    <form:hidden path="version"/>     
	    
    <acme:textbox path="userAccountuser" code="administrator.userAccount.username"/>
    <br/>
    <acme:textbox path="userAccountpassword" code="administrator.userAccount.password"/>
    <br/>
    <acme:textbox path="name" code="administrator.name"/>
    <br/>
    <acme:textbox path="middleName" code="administrator.middleName"/>
    <br/>
    <acme:textbox path="surname" code="administrator.surname"/>
    <br/>
    <acme:textbox path="photo" code="administrator.photo"/>
    <br/>
    <acme:textbox path="email" code="administrator.email"/>
    <br/>
    <acme:textbox path="phone" code="administrator.phone"/>
    <br/>
    <acme:textbox path="address" code="sponsor.address"/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>


    <button name="save" type="submit" class="button2">
        <spring:message code="administrator.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="administrator.cancel" />"
           onclick="relativeRedir('')"/>

</form:form>
