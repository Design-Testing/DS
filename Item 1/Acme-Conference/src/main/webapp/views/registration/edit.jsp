<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="today" value="${now}" pattern="dd/MM/yyyy HH:mm" />

<form:form action="application/${rol}/edit.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />



	<security:authorize access="hasRole('HANDYWORKER')">

		<jstl:choose>
			<jstl:when test="${application.id eq 0}">
				<form:hidden path="status" value="PENDING" />
				<form:hidden path="moment" value="${today}" />
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="status" />
				<form:hidden path="moment" />
			</jstl:otherwise>
		</jstl:choose>

		<form:label path="price">
			<spring:message code="application.price" />:
		</form:label>
		<form:input path="price" />
		<form:errors cssClass="error" path="price" />
		<br />

		<form:label path="comments">
			<spring:message code="application.comments" />:
		</form:label>
		<form:textarea path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br />
		<form:label path="task">
			<spring:message code="application.task" />:
		</form:label>
		<form:select id="tasks" path="task">
			<form:options items="${tasksAvailable}" itemLabel="ticker"
				itemValue="id" />
			<jstl:if test="${not empty task}">
				<form:option value="${task}" label="${task.ticker}"
					selected="selected" />
			</jstl:if>
		</form:select>
		<form:errors cssClass="error" path="task" />
		<br />

		<input type="submit" name="save"
			value="<spring:message code="application.save" />" />

	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="moment" />
		<form:hidden path="comments" />
		<form:hidden path="task" />

		<spring:message code="application.moment" />:
		<jstl:out value="${application.moment}" />
		<br />

		<jstl:set var="newPrice" value="${(application.price*vat)/100}" />

		<spring:message code="application.price" />:
		<jstl:out value="${application.price} (+${newPrice}" />
		<spring:message code="application.vat" />)
		<br />

		<spring:message code="application.comments" />:
		<ul>
			<jstl:forEach var="comment" items="${application.comments}">
				<li><jstl:out value="${comment}" /></li>
			</jstl:forEach>
		</ul>
		<br />

		<spring:message code="application.task.ticker" />:
		<jstl:out value="${application.task.ticker}" />
		<br />
		<br />

		<jstl:choose>
			<jstl:when test="${mostrarTarjetas eq 'ACCEPTED'}">
				<form:hidden path="status" value="ACCEPTED" />
				<form:label path="creditCard">
					<spring:message code="application.creditCard" />:
			</form:label>
				<form:select id="cards" path="creditCard">
					<jstl:forEach var="card" items="${myCards}">
						<form:option value="${card.id}">
							<jstl:out value="${card.brandName } - number: ${card.number}" />
						</form:option>
					</jstl:forEach>
				</form:select>
				<form:errors cssClass="error" path="creditCard" />
				<br />
				<br />
			</jstl:when>
			<jstl:otherwise>
				<jstl:if test="${application.status eq 'PENDING'}">
					<form:label path="status">
						<spring:message code="application.status" />: </form:label>
					<form:select id="statuss" path="status">
						<form:option value="PENDING">
							<spring:message code="application.status.pending" />
						</form:option>
						<form:option value="ACCEPTED">
							<spring:message code="application.status.accepted" />
						</form:option>
						<form:option value="REJECTED">
							<spring:message code="application.status.rejected" />
						</form:option>

					</form:select>

					<form:errors cssClass="error" path="status" />
					<br />
					<br />

				</jstl:if>
			</jstl:otherwise>
		</jstl:choose>
		<jstl:if test="${application.status eq 'PENDING'}">
			<input type="submit" name="save"
				value="<spring:message code="application.save" />" />
		</jstl:if>

		<%
			if (application.getAttribute("creditCard") != null) {
		%>
		<input type="button" name="creditCardDisplay"
			value="<spring:message code="creditCard.display" />"
			onclick="javascript: relativeRedir('creditCard/customer/display.do?applicationId=${application.id}');" />
		<%
			}
		%>
		<form:hidden path="comments" />

	</security:authorize>



	<input type="button" name="cancel"
		value="<spring:message code="application.cancel"/>"
		onclick="javascript:relativeRedir('application/${rol}/list.do');" />


</form:form>
