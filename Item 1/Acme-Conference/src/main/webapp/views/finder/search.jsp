<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
<jstl:when test="${not empty errortrace}">
	<h1>Error</h1>
	<h3><spring:message code="${errortrace}"/></h3>
</jstl:when>
<jstl:otherwise>
<jstl:set var="keyword"/>

<jstl:set value="finder/search.do?keyword=${keyword}" var="uri"/>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set value="finder/administrator/search.do?keyword=${keyword}" var="uri"/>
</security:authorize>
<form action="${uri}" method="GET">
	<label for="keyword"><spring:message code="finder.keyword" />:</label>
	<input type="text" id="keyword" name="keyword"/><br>
	<jstl:out value="${keyword}"></jstl:out>	
	
	<input type="submit" value="<spring:message code="finder.search" />" />
	<br>
	<br>
</form>

<spring:message code="finder.results" />
<br>
<security:authorize access="hasRole('AUTHOR')">
<jstl:set var="rolURL" value="/author"/>
</security:authorize>
<display:table name="${conferences}" requestURI="/finder/search.do" id="row" pagesize="15" class="displaytag">
	<display:column property="title" titleKey="conference.title" />
	<display:column property="acronym" titleKey="conference.acronym" />
	<display:column property="startDate" titleKey="conference.startDate" />
	<display:column property="endDate" titleKey="conference.endDate" />
	
	<display:column>
		<acme:link url="conference${rolURL}/display.do?conferenceId=${row.id}"
			code="conference.display" />
	</display:column>
	
	
</display:table>
</jstl:otherwise>
</jstl:choose>