<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="conferences" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

	<display:column property="title" titleKey="conference.title" />
	
	<display:column property="acronym" titleKey="conference.acronym" />
	
	
	<jstl:choose>
	<jstl:when test="${isAdministrator eq true }">
		
		<display:column>
			<acme:button url="conference/admistrator/display.do?conferenceId=${row.id}" name="display" code="conference.display"/>
		</display:column>
	
	</jstl:when>
	<jstl:when test="${isAdministrator eq false }">
	
		<display:column>
		<jstl:if test=${row.isDraft eq true } >
		
		
		</jstl:if>
		<jstl:if test=${row.isDraft eq false } >
			<acme:button url="conference/display.do?conferenceId=${row.id}" name="display" code="conference.display"/>
		</jstl:if>
			
		</display:column>
		
	</jstl:when>
	</jstl:choose>
	
	
	<display:column>
		<acme:button url="conference/display.do?lessonId=${row.id}" name="display" code="lesson.display"/>
	</display:column>
	
	<security:authorize access="hasRole('TEACHER')">
		<display:column>
			<jstl:if test="${row.isDraft}">
				<acme:button url="lesson/teacher/edit.do?lessonId=${row.id}&subjectId=${row.subject.id}" name="edit" code="lesson.edit"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${row.isDraft}">
				<acme:button url="lesson/teacher/finalMode.do?lessonId=${row.id}" name="finalMode" code="lesson.finalMode"/>
			</jstl:if>
		</display:column>
		
			<jstl:choose>
				<jstl:when test="${not empty reservations}">
				</jstl:when>
				<jstl:otherwise>
					<display:column>
					<acme:button url="lesson/teacher/delete.do?lessonId=${row.id}" name="delete" code="lesson.delete"/>
					</display:column>
				</jstl:otherwise>
			</jstl:choose>
		
	</security:authorize>
	
	<security:authorize access="hasRole('STUDENT')">
			<display:column>
				<acme:button url="assesment/student/create.do?lessonId=${row.id}" name="create" code="lesson.assesment.create"/>
			</display:column>
		</security:authorize>

</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;"><spring:message code="${msg}"/></h3>
</jstl:if>