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

<%@ attribute name="activity" required="true"%>
<%@ attribute name="lang" required="true"%>

<acme:display code="activity.title" value="${value.title}" />
<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="activity.startMoment" />: <fmt:formatDate
			value="${value.startMoment}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="activity.startMoment" />: <fmt:formatDate
			value="${value.startMoment}" type="both" pattern="dd/MM/yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>
<br/>
<acme:display code="activity.hours" value="${value.hours}" />
<acme:display code="activity.minutes" value="${value.minutes}" />
<acme:display code="activity.room" value="${value.room}" />
<acme:display code="activity.summary" value="${value.summary}" />
<acme:ulist code="activity.attachments" items="${value.attachments}" />
<acme:ulist code="activity.speakers" items="${value.speakers}" />
<br />