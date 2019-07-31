<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<form:form action="submission/author/edit.do" modelAttribute="paper">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    
    
    
    <acme:textbox path="title" code="paper.title"/>
    <br/>
    
    <acme:textarea path="summary" code="paper.summary"/>
    <br/>

    <acme:textarea path="document" code="paper.document"/>
    <br/>
    
     <acme:textarea path="authors" code="paper.authors"/>
    <br/>
    
   
    
    
    

      
	
    <br/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>

    
	


    <!---------------------------- BOTONES -------------------------->


    <button name="save" type="submit" class="button2">
        <spring:message code="paper.save"/>
    </button>

  

</form:form>