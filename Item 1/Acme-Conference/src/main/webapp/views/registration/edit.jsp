<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('AUTHOR')">
<form:form action="registration/author/edit.do"
	modelAttribute="registrationForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author"/>
	<form:hidden path="conference" />
	
	<h4><spring:message code="creditCard" /></h4>

	<acme:textbox code="creditcard.holderName" path="holderName"/>
	<acme:numberbox code="creditcard.number" path="number" min="0"/>
	
	<form:label path="make">
		<spring:message code="creditcard.make" />
	</form:label>	
	<form:select path="make" >
		<form:options items="${makes}" />
	</form:select>
	<form:errors path="make" cssClass="error" />
	
	<acme:numberbox code="creditcard.expirationMonth" path="expirationMonth" min="0"/>
	<acme:numberbox code="creditcard.expirationYear" path="expirationYear" min="0"/>
	<acme:textbox code="creditcard.cvv" path="cvv"/><br/>
	
	<acme:display code="registration.conference.fee" value="${registrationForm.conference.fee}"/><br/>

	<acme:submit name="save" code="registration.save"/>


	<acme:button url="registration/author/list.do" name="cancel" code="registration.cancel"/>

</form:form>
	</security:authorize>