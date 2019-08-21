<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
     
    
    <form:hidden path="id"/>
    <form:hidden path="version"/>     
    <form:hidden path="sponsor"/>
    
    <acme:textbox path="banner" code="sponsorship.banner"/>
    <br/>
    
    <acme:textbox path="targetPage" code="sponsorship.targetPage"/>
    <br/>
    
    <acme:textbox path="creditCard.holderName" code="sponsorship.creditCard.holderName"/>
    <br/>
    <spring:message code="sponsorship.creditCard.make"/>
    <form:select path="creditCard.make" code="sponsorship.creditCard.make">
    	<jstl:forEach items="${makes}" var="make">
    			<form:option value="${make}" label="${make}"/>
    	</jstl:forEach>
    </form:select>
    <br>
    <br/>
    <acme:textbox path="creditCard.number" code="sponsorship.creditCard.number"/>
    <br/>
    <acme:textbox path="creditCard.cvv" code="sponsorship.creditCard.cvv"/>
    <br/>
    <acme:textbox path="creditCard.expirationMonth" code="sponsorship.creditCard.expirationMonth"/>
    <br/>
    <acme:textbox path="creditCard.expirationYear" code="sponsorship.creditCard.expirationYear"/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>


    <button name="save" type="submit" class="button2">
        <spring:message code="sponsorship.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancell"
           value="<spring:message code="sponsorship.cancell" />"
           onclick="relativeRedir('sponsorship/sponsor/display.do?sponsorshipId=${sponsorship.id}');"/>

</form:form>
