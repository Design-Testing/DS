
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:set value="finder/actor/edit.do" var="uri"/>
<security:authorize access="hasRole('ADMIN')">
	<jstl:set value="finder/administrator/edit.do" var="uri"/>
	<jstl:set value="/administrator" var="rolURL"/>
</security:authorize>
<form:form action="${uri}" modelAttribute="finder" method="POST">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="finder.keyword" path="keyword" />
	<acme:textbox code="finder.fromDate" path="fromDate"
		placeholder="yyyy-MM-dd HH:mm" />
	<acme:textbox code="finder.toDate" path="toDate"
		placeholder="yyyy-MM-dd HH:mm" />

	<form:label path="categoryName">
		<spring:message code="finder.categoryName" />
	</form:label>	
	<form:select path="categoryName">
		<form:option value="" label="----" />		
		<form:options items="${categories}"/>
	</form:select>
	<form:errors path="categoryName" cssClass="error" />
				
	<acme:numberbox min="0" code="finder.maximumFee" path="maximumFee"/>
	<br>
	<jstl:if test="${deadnul}">
		<spring:message code="finder.n.deadline" />
	</jstl:if>
	<br>
	<br>
	<input type="submit" name="save"
		value="<spring:message code="finder.search" />" />
	<input type="submit" name="clear"
		value="<spring:message code="finder.clear" />" />
	<br>
	<br>
	<spring:message code="finder.results" />
	<br>


	<display:table name="${finder.conferences}" id="row"
		requestURI="/finder/actor/edit.do" pagesize="15" class="displaytag">
		<display:column property="title" titleKey="conference.title" />
		<display:column property="acronym" titleKey="conference.acronym" />
		<display:column property="startDate" titleKey="conference.startDate" />
		<display:column property="endDate" titleKey="conference.endDate" />

		<display:column>
			<acme:link
				url="conference${rolURL}/display.do?conferenceId=${row.id}"
				code="conference.display" />
		</display:column>


	</display:table>
	
</form:form>