<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('ADMIN')">

<display:table name="reviewers" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
    			<display:column property="userAccount.username" titleKey="reviewer.userAccount.username" />
    			<display:column property="name" titleKey="reviewer.name" />
    			<display:column property="middleName" titleKey="reviewer.middleName" />
    			<display:column property="surname" titleKey="reviewer.surname" />
    			<display:column property="keywords" titleKey="reviewer.keywords" />
			
		
</display:table>

 <input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="reviewer.back" />"
           onclick="relativeRedir('conference/administrator/display.do?conferenceId=${submission.conference.id}');"/>
           
</security:authorize>