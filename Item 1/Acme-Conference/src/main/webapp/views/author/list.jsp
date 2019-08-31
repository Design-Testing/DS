<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="authors" id="row" requestURI="administrator/author/list.do"
	pagesize="5" class="displaytag">

	<display:column property="name" titleKey="author.name" />

	<display:column property="surname" titleKey="author.surname" />

	<display:column property="email" titleKey="author.email" />

	<display:column property="phone" titleKey="author.phone" />

	<jstl:choose>
		<jstl:when test="${(not empty row.score) and (row.score ne 0.)}">
			<display:column titleKey="author.score">${row.score}</display:column>
		</jstl:when>
		<jstl:otherwise>
			<display:column titleKey="author.score">
				<jstl:out value="N/A"></jstl:out>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>

</display:table>

<jstl:if test="${(not empty maxScore) and (maxScore ne 0)}">
	<acme:display code="max.score" value="${maxScore}" />
</jstl:if><br/>

<security:authorize access="hasRole('ADMIN')">
	<acme:button url="administrator/author/computeScore.do" name="score"
		code="author.compute.score" />
</security:authorize>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;">
		<spring:message code="${msg}" />
	</h3>
</jstl:if>