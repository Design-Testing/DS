<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:set value="comment/list.do?entity=${entity}&id=${id}" var="requestURI"/>

<display:table name="comments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column property="title" titleKey="comment.title" />
	<display:column>
		<acme:button url="comment/display.do?commentId=${row.id}&entity=${entity}&entityId=${id}"
			name="display" code="comment.display" />
	</display:column>

	<display:column>
	<jstl:if test="${not empty principalId and not empty row.author and (principalId eq row.author.id) and (entity eq 'report')}">
		<acme:button url="comment/edit.do?commentId=${row.id}" name="display"
			code="comment.edit" />
	</jstl:if>
	</display:column>

</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;">
		<spring:message code="${msg}" />
	</h3>
</jstl:if>
<jstl:choose>
	<jstl:when test="${entity eq 'report'}">
	</jstl:when>
	<jstl:otherwise>
		<acme:button url="comment/create.do?entity=${entity}&entityId=${id}" name="create" code="comment.create" />
	</jstl:otherwise>
</jstl:choose>