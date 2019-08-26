<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${empty registrations }">
		<spring:message code="registration.no.list" />
	</jstl:when>
	<jstl:otherwise>
		<security:authorize access="hasRole('AUTHOR')">
			<display:table name="registrations" id="row"
				requestURI="registration/author/list.do" pagesize="5"
				class="displaytag">

				<display:column>
					<acme:button code="registration.display" url="registration/author/display.do?registrationId=${row.id}" name="display"/>
				</display:column>

				<display:column property="author.name" titleKey="registration.author" />

				<display:column property="conference.title" titleKey="registration.conference" />

				<display:column property="creditCard.number" titleKey="creditcard.number"/>

				<display:column>
					<acme:button code="registration.edit" url="registration/author/edit.do?registrationId=${row.id}" name="edit"/>
				</display:column>
				
			</display:table>
		</security:authorize>

	</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:choose>
		<jstl:when test="${theresConferencesAvailable}">
			<input type="button" name="create"
				value="<spring:message code="registration.create" />"
				onclick="javascript: relativeRedir('registration/author/create.do');" />
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="registration.create.no" />
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>
