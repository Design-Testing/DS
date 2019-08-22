<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="title" required="true"%>
<%@ attribute name="room" required="true"%>
<%@ attribute name="hours" required="true"%>
<%@ attribute name="minutes" required="true"%>
<%@ attribute name="summary" required="true"%>
<%@ attribute name="lang" required="true"%>

<jstl:choose>
	<jstl:when test="${not empty title}">
		<acme:display code="activity.title" value="${title}" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.title" /> <spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>

<jstl:choose>
	<jstl:when test="${not empty hours}">
		<acme:display code="activity.hours" value="${hours}" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.hours" /> <spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>
<jstl:choose>
	<jstl:when test="${not empty summary}">
		<acme:display code="activity.summary" value="${summary}" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.summary" /> <spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>
<jstl:choose>
	<jstl:when test="${not empty room}">
		<acme:display code="activity.room" value="${room}" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.room" /> <spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>
<jstl:choose>
	<jstl:when test="${not empty minutes}">
		<acme:display code="activity.minutes" value="${minutes}" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.minutes" /> <spring:message code="empty" />
		<br />
	</jstl:otherwise>
</jstl:choose>
