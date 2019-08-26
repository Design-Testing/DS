<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


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
					<a href="registration/author/display.do?registrationId=${row.id}">
						<spring:message code="registration.display" />
					</a>
				</display:column>

				<display:column property="author" titleKey="registration.author" />

				<display:column property="conference" titleKey="registration.conference" />

				<display:column property="creditCard.number" titleKey="registration.creaditCard.number"/>

				<display:column>
					<a href="registration/author/edit.do?registrationId=${row.id}">
						<spring:message code="registration.edit" />
					</a>
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
