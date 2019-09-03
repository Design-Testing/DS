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
<jstl:set var="category"/>

<form action="conference/listByCategory.do?category=${category}" method="GET">
	<label for="category"><spring:message code="finder.categoryName" />:</label>
	<select name="category" >
	<option value="" selected><spring:message code="choose.category"/></option>
		<jstl:forEach items="${categories}" var="r">
			<option value="<jstl:out value='${r}'/>"><jstl:out value="${r}"/></option>
		</jstl:forEach>
	</select>
	<input type="submit" value="<spring:message code="conference.category.sumbit" />" />
	<jstl:if test="${not empty cate}">
	<br/><br/>
		<spring:message code="category.selection"/> <strong><jstl:out value="${cate}"/></strong>
	<br/>	
	</jstl:if>
	<br>
	<br>
</form>

<spring:message code="finder.results" />
<br>
<security:authorize access="hasRole('AUTHOR')">
<jstl:set var="rolURL" value="/author"/>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<jstl:set var="rolURL" value="/administrator"/>
</security:authorize>
<display:table name="${conferences}" requestURI="/conference/listByCategory.do" id="row" pagesize="15" class="displaytag">
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