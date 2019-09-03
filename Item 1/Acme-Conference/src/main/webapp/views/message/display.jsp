<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


</style>

	<acme:display code="sender" value="${m.sender.name}" />
	<acme:display code="subject" value="${m.subject}" />
	<acme:display code="moment" value="${m.moment}" />
	<jstl:choose>
		<jstl:when test="${lang eq 'en' }">
			<acme:display code="topic" value="${m.topic.english}" />
		</jstl:when>
		<jstl:otherwise>
			<acme:display code="topic" value="${m.topic.spanish}" />
		</jstl:otherwise>
	</jstl:choose>
	<spring:message code="recivers" />:
	<div style="height:240px;width:180px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;">
		<ul>
			<jstl:forEach items="${m.recivers}" var="r">
				<li><jstl:out value="${r}" /></li>
			</jstl:forEach>
		</ul>
	</div>
	<acme:display code="body" value="${m.body}" />
	



	
	
