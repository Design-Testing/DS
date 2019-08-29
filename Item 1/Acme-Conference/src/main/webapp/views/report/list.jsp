<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="reports" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="submission.ticker" titleKey="report.submission.ticker" />
	<display:column titleKey="report.decision">
		<jstl:choose>
			<jstl:when test="${row.isDraft}">
				<spring:message code="report.isDraft" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="report.final" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
		<display:column>
		<jstl:if test="${row.isDraft}">
			<button onclick="location.href = 'report/edit.do?reportId=${row.id}'">
				<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAMAAADXqc3KAAAAUVBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA3C4bvAAAAGnRSTlMAlZY5fFwTgH9X/PvwQIjvP1vVvsDx9BEVPrZ1NJEAAABgSURBVCjPvZFJEoAgDAQDAnHHfcn/H6rgkeFk6Ry7s1QlRO9j2gpyFqltRkiT9gyP8Wk5R2PAmGAYcJEerg3pMrz8mZPTkXN6oQNyWxDtGtSr9TYOzFlkO+FvplmN9GEuZT4JYKeoDlYAAAAASUVORK5CYII=">
			</button>
		</jstl:if>
	</display:column>
	<display:column>
		<acme:button url="comment/list.do?entity=report&id=${row.id}"
			name="list" code="comments" />
	</display:column>
	<display:column>
		<jstl:if test="${row.isDraft}">
			<acme:button
				url="comment/create.do?entityId=${row.id}&entity=report"
				name="new" code="add.comment" />
		</jstl:if>
	</display:column>
</display:table>
