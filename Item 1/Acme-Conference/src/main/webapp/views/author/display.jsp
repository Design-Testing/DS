<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



	<acme:display code="author.userAccount.username" value="${author.userAccount.username}" />
	<acme:display code="author.name" value="${author.name}" />
	<acme:display code="author.middleName" value="${author.middleName}" />
	
	<dt><spring:message code="author.surname" />:</dt>
	<dd>
		<jstl:forEach items="${author.surname}" var="surname">
    			<jstl:out value="${surname}"/>
    	</jstl:forEach></dd>
	
	<dt><spring:message code="author.photo" />:</dt>
	<dd>
	<img src="${author.photo}" alt="<jstl:out value ="${author.photo}"/>" width="220" height="135"/>
	</dd>
	<acme:display code="author.email" value="${author.email}" />
	<acme:display code="author.phone" value="${author.phone}" />
	<acme:display code="author.address" value="${author.address}" />
	<acme:display code="author.score" value="${author.score}" />

	
	

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="author.edit" />"
           onclick="relativeRedir('author/edit.do')"/>
           
<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="author.back" />"
           onclick="relativeRedir('');"/>