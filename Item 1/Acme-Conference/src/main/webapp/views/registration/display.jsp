<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:display code="registration.conference" value="${registration.conference.title}" url="conference/display.do?conferenceId=${registration.conference.id}"/>
<acme:display code="registration.author" value="${registration.author.name}"/>

<h4><spring:message code="creditCard" /></h4>

<acme:display code="creditcard.holderName" value="${registration.creditCard.holderName}"/>
<acme:display code="creditcard.number" value="${registration.creditCard.number}"/>
<acme:display code="creditcard.expirationMonth" value="${registration.creditCard.expirationMonth}"/>
<acme:display code="creditcard.expirationYear" value="${registration.creditCard.expirationYear}"/>
<acme:display code="creditcard.cvv" value="${registration.creditCard.cvv}"/>
<br/>
<acme:display code="registration.conference.fee" value="${registration.conference.fee}"/>

<br />
<acme:button code="registration.back" name="back" url="registration/${rol}/list.do"/>











