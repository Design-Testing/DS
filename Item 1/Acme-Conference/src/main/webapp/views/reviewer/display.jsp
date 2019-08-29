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
<acme:display code="reviewer.keywords" value="${reviewer.keywords}" />

<jstl:set value="${reviewer.keywords.size()}" var="s1" />
<jstl:choose>
	<jstl:when test="${not empty reviewer.keywords and (s1 gt 1)}">
		<spring:message code="activity.attachments" />
		<jstl:forEach items="${reviewer.keywords}" var="line">
			<ul>
				<li><a href="${line}"><jstl:out value="${line}" /></a></li>
			</ul>
		</jstl:forEach>
	</jstl:when>
	<jstl:when test="${not empty reviewer.keywords and (s1 le 1)}">
		<spring:message code="activity.attachments" />: 
			<a href="${reviewer.keywords.get(0)}"><jstl:out
				value="${reviewer.keywords.get(0)}" /></a>
		<br />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.attachments" />
		<spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>

<acme:display code="reviewer.address" value="${reviewer.address}" />




<input type="button" class="btn btn-danger" name="edit"
	value="<spring:message code="reviewer.edit" />"
	onclick="relativeRedir('reviewer/edit.do')" />

<input type="button" class="btn btn-danger" name="back"
	value="<spring:message code="reviewer.back" />"
	onclick="relativeRedir('');" />