<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<acme:showActivity value="${tutorial}" lang="${lang}"/>
	
	<display:table name="${tutorial.sections}" id="row"
	requestURI="section/list.do?tutorialId=${tutorial.id}" pagesize="5"
	class="displaytag">

	<display:column property="title" titleKey="section.title" />
	<display:column>
		<security:authorize access="hasRole('ADMIN')">
			<acme:button
				url="section/edit.do?sectionId=${row.id}&tutorialId=${tutorial.id}"
				name="edit" code="section.edit" />
		</security:authorize>
	</display:column>
	<display:column>
	<security:authorize access="hasRole('ADMIN')">
	<acme:button
		url="section/edit.do?sectionId=${row.id}&tutorialId=${tutorial.id}"
		name="edit" code="section.edit" />	
	</security:authorize>
	</display:column>
	</display:table>
	<security:authorize access="hasRole('ADMIN')">
			<acme:button
				url="section/create.do?tutorialId=${tutorial.id}"
				name="edit" code="section.create" />
	</security:authorize>
	
	<acme:button url="tutorial/list.do" name="back" code="tutorial.back" />
	
	<!-- LIssta de sections dentro del tutorial -->
	