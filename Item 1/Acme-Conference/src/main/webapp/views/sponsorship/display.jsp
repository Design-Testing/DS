<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<acme:display code="sponsorship.banner" value="${sponsorship.banner}" />
	<acme:display code="sponsorship.targetPage" value="${sponsorship.targetPage}" />
    <h3><spring:message code="sponsorship.creditCard" /></h3>
    <acme:display code="sponsorship.creditCard.holderName" value="${sponsorship.creditCard.holderName}" />
	<acme:display code="sponsorship.creditCard.make" value="${sponsorship.creditCard.make}" />
	<acme:display code="sponsorship.creditCard.number" value="${sponsorship.creditCard.number}" />
	<acme:display code="sponsorship.creditCard.cvv" value="${sponsorship.creditCard.cvv}" />
	<acme:display code="sponsorship.creditCard.expirationMonth" value="${sponsorship.creditCard.expirationMonth}" />
	<acme:display code="sponsorship.creditCard.expirationYear" value="${sponsorship.creditCard.expirationYear}" />
	
	
	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="sponsorship.edit" />"
           onclick="relativeRedir('sponsorship/sponsor/edit.do?sponsorshipId=${sponsorship.id}');"/>
           
<input type="button" class="btn btn-danger" name="delete"
           value="<spring:message code="sponsorship.delete" />"
           onclick="relativeRedir('sponsorship/sponsor/delete.do?sponsorshipId=${sponsorship.id}');"/>

<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="sponsorship.back" />"
           onclick="relativeRedir('sponsorship/sponsor/list.do');"/>