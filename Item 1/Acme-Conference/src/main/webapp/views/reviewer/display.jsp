<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<acme:display code="reviewer.userAccount.username"
	value="${reviewer.userAccount.username}" />
<acme:display code="reviewer.name" value="${reviewer.name}" />
<acme:display code="reviewer.middleName" value="${reviewer.middleName}" />
<acme:display code="reviewer.surname" value="${reviewer.surname}" />
<spring:message code="author.photo" />:
<br />
<img src="${author.photo}" alt="<jstl:out value ="${author.photo}"/>"
	width="220" height="135" />
<acme:display code="reviewer.email" value="${reviewer.email}" />
<acme:display code="reviewer.phone" value="${reviewer.phone}" />

<spring:message code="reviewer.keywords" />:
<ul>
	<jstl:forEach items="${reviewer.keywords}" var="word">
		<li><jstl:out value="${word}" /></li>
	</jstl:forEach>
</ul>
<acme:display code="reviewer.address" value="${reviewer.address}" />




<input type="button" class="btn btn-danger" name="edit"
	value="<spring:message code="reviewer.edit" />"
	onclick="relativeRedir('reviewer/edit.do')" />

<input type="button" class="btn btn-danger" name="back"
	value="<spring:message code="reviewer.back" />"
	onclick="relativeRedir('');" />