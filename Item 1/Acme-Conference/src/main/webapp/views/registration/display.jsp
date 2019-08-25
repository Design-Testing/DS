<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="application.moment" />
:
<jstl:out value="${application.moment}" />
<br />

<spring:message code="application.status" />
:
<jstl:out value="${application.status}" />
<br />

<jstl:set var="newPrice" value="${(application.price*vat)/100}" />

<spring:message code="application.price" />
:
<jstl:out value="${application.price} (+${newPrice} " /><spring:message code="application.vat" />)
<br />

<spring:message code="application.comments" />
:
<ul>
	<jstl:forEach var="comment" items="${application.comments}">
		<li><jstl:out value="${comment}" /></li>
	</jstl:forEach>
</ul>
<br />

<spring:message code="application.task.ticker" />
:
<jstl:out value="${application.task.ticker}" />
<br />

<input type="button" name="back"
	value="<spring:message code="application.back" />"
	onclick="javascript: relativeRedir('application/${rol}/list.do');" />
<br />











