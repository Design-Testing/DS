<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	
<acme:display code="quolet.ticker" value="${quolet.ticker}" />

<jstl:choose>
	<jstl:when test="${lang eq 'en'}">
		<spring:message code="quolet.moment" />: <fmt:formatDate
			value="${quolet.moment}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="quolet.moment" />: <fmt:formatDate
			value="${quolet.moment}" type="both" pattern="dd-MM-yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>
<br>

<acme:display code="quolet.body" value="${quolet.body}" />


<jstl:choose>
	<jstl:when test="${quolet.isDraft}">
		<spring:message code="quolet.mode"/>: <spring:message code="quolet.isDraft.true"/>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="quolet.mode"/>: <spring:message code="quolet.isDraft.false"/>
	</jstl:otherwise>
</jstl:choose>
<br>
<jstl:if test="${not empty quolet.picture}">
<spring:message code="quolet.picture"/>:<br>
<img src="${quolet.picture}" alt="<spring:message code="quolet.alt.image"/> ${quolet.picture}" width="20%" height="20%"/>
<br><br></jstl:if>

<acme:button url="audit/auditor/display.do?auditId=${quolet.audit.id}" name="back" code="quolet.back"/>
<br>