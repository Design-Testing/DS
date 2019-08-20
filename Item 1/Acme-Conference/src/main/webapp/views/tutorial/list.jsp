<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="tutorials" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="title" titleKey="activity.title" />
	<display:column>
		<jstl:choose>
			<jstl:when test="${lang eq 'en' }">
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${activity.startMoment}" type="both"
					pattern="yyyy/MM/dd HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="activity.startMoment" />: <fmt:formatDate
					value="${activity.startMoment}" type="both"
					pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	<display:column property="hours" titleKey="activity.hours" />
	<display:column property="room" titleKey="activity.room" />
	<jstl:if test="${not empty conferenceId}">
	<display:column>
			<acme:button
				url="tutorial/display.do?conferenceId=${conferenceId}&tutorialId=${row.id}"
				name="display" code="activity.display" />
	</display:column>
	<display:column>
		<security:authorize access="hasRole('ADMIN')">
			<acme:button
				url="tutorial/edit.do?conferenceId=${conferenceId}&tutorialId=${row.id}"
				name="edit" code="activity.edit" />
		</security:authorize>
	</display:column>
	</jstl:if>
</display:table>
