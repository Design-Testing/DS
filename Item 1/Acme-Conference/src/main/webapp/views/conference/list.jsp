<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${isAdministrator eq true }" >

 <input type="button" class="btn btn-danger" name="create"
           value="<spring:message code="conference.create" />"
           onclick="relativeRedir('conference/administrator/create.do');"/>
</jstl:if>

<display:table name="conferences" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

	<display:column property="title" titleKey="conference.title" />
	
	
	
	<jstl:choose>
	<jstl:when test="${isAdministrator eq true }">
		
		<display:column>
			<acme:button url="conference/administrator/display.do?conferenceId=${row.id}" name="display" code="conference.display"/>
		</display:column>
		
		<display:column>
		<jstl:if test="${row.isDraft eq true }" >
			<acme:button url="conference/administrator/edit.do?conferenceId=${row.id}" name="display" code="conference.edit"/>		
		</jstl:if>
		<jstl:if test="${row.isDraft eq false }" >
			-
		</jstl:if>
		</display:column>
		
		<display:column>
		<jstl:if test="${row.isDraft eq true }" >
			<acme:button url="conference/administrator/finalMode.do?conferenceId=${row.id}" name="display" code="conference.toFinal"/>		
		</jstl:if>
		<jstl:if test="${row.isDraft eq false }" >
			<spring:message code="conference.final"/>
		</jstl:if>
		</display:column>
	
	</jstl:when>
	<jstl:when test="${isAdministrator eq false }">
	
		<display:column>
		<jstl:if test="${row.isDraft eq true }" >
			<spring:message code="conference.not.final"/>		
		</jstl:if>
		<jstl:if test="${row.isDraft eq false }" >
			<acme:button url="conference/display.do?conferenceId=${row.id}" name="display" code="conference.display"/>
		</jstl:if>
			
		</display:column>
		
		<display:column>
		<jstl:if test="${isAuthor eq true and row.isDraft eq false }" >
			<acme:button url="submission/author/create.do?conferenceId=${row.id}" name="display" code="conference.submits"/>
		</jstl:if>
		</display:column>
		
	</jstl:when>
	</jstl:choose>
	
	
	

</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;"><spring:message code="${msg}"/></h3>
</jstl:if>