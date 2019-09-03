<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



	<acme:display code="sponsor.userAccount.username" value="${sponsor.userAccount.username}" />
	<acme:display code="sponsor.name" value="${sponsor.name}" />
	<acme:display code="sponsor.middleName" value="${sponsor.middleName}" />
	<acme:display code="sponsor.surname" value="${sponsor.surname}" />
	
	<spring:message code="sponsor.photo" />:
	</br>
	<img src="${sponsor.photo}" alt="<jstl:out value ="${sponsor.photo}"/>" width="220" height="135"/>
	</br>
	<acme:display code="sponsor.email" value="${sponsor.email}" />
	<acme:display code="sponsor.phone" value="${sponsor.phone}" />
	<acme:display code="sponsor.address" value="${sponsor.address}" />

	
	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="sponsor.edit" />"
           onclick="relativeRedir('sponsor/edit.do?sponsorId=${sponsor.id}');"/>
           
<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="sponsor.back" />"
           onclick="relativeRedir('');"/>