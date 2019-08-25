<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style type="text/css">
.pendingPeriodPassed {
	color: grey;
}

.ACCEPTED {
	color: green;
}

.REJECTED {
	color: orange;
}
</style>

<jstl:choose>
	<jstl:when test="${empty registrations }">
		<spring:message code="registration.no.list" />
	</jstl:when>
	<jstl:otherwise>
		<display:table name="registrations" id="row"
			requestURI="registration/${rol}/list.do" pagesize="5"
			class="displaytag">

			<security:authorize access="hasRole('CUSTOMER')">
				<display:column>
					<a href="registration/customer/edit.do?registrationId=${row.id}">
						<spring:message code="registration.display" />
					</a>
				</display:column>
			</security:authorize>

			<jstl:set value="${row.status} " var="colorStyle" />
			<jsp:useBean id="currentDate" class="java.util.Date" />
			<fmt:formatDate var="now" value="${currentDate}"
				pattern="dd/MM/yyyy HH:mm" />
			<jstl:if
				test="${(row.status eq 'PENDING') and (now > row.task.endDate)}">
				<jstl:set value="pendingPeriodPassed" var="colorStyle" />
			</jstl:if>

			<display:column property="moment" titleKey="registration.moment"
				sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column property="status" titleKey="registration.status"
				class="${colorStyle}" />

			<display:column property="task.ticker"
				titleKey="registration.task.ticker" />

			<security:authorize access="hasRole('HANDYWORKER')">
				<display:column>
					<a
						href="registration/author/display.do?registrationId=${row.id}">
						<spring:message code="registration.display" />
					</a>
				</display:column>
			</security:authorize>


		</display:table>
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

<jstl:if test="${noCards}">
	<spring:message code="registration.creditCard.no" />
</jstl:if>