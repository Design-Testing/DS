<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="${action}" modelAttribute="m">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />


	<jstl:choose>
		<jstl:when test="${isBroadcast != true}">
		<form:label path="recivers">
		<spring:message code="recivers" />:
	    </form:label>
		<form:select path="recivers" code="recivers">
			<jstl:forEach items="${actors}" var="r">
				<form:option value="${r.id}" label="${r.name}" />
			</jstl:forEach>
		</form:select>
		<form:errors path="recivers" cssClass="error" />
		</jstl:when>
		<jstl:otherwise>
			<form:hidden path="recivers" />
		</jstl:otherwise>
	</jstl:choose>

	
	<br />
	<br />

	<acme:textbox path="subject" code="subject" />
	<br />

	<acme:textarea path="body" code="body" />
	<br />


	<form:label path="topic">
		<spring:message code="topic" />:
    </form:label>
	<form:select path="topic" code="topic">
		<jstl:forEach items="${topics}" var="rt">
			<jstl:choose>
				<jstl:when test="${lang eq 'en' }">
					<form:option value="${rt.id}" label="${rt.english}" />
				</jstl:when>
				<jstl:otherwise>
					<form:option value="${rt.id}" label="${rt.spanish}" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</form:select>
	<form:errors path="topic" cssClass="error" />
	<br />
	<br />

	<button name="save" type="submit" class="button">
		<spring:message code="send" />
	</button>
	<jstl:if test="${isAdmin and isBroadcast != true}">
		<button type="submit" formaction="message/broadcastAuthors.do" class="button">
			<spring:message code="broadcastAuthors" />
		</button>
		<button type="submit" formaction="message/broadcastActors.do" class="button">
			<spring:message code="broadcastActors" />
		</button>
	</jstl:if>
</form:form>

