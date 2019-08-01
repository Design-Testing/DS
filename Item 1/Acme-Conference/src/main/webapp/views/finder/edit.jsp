
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="finder/edit.do" modelAttribute="finder" method="POST">

    
    <form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="conferences"/>
	
	<acme:textbox code="finder.keyword" path="keyword"/>
	<acme:textbox code="finder.fromDate" path="minDeadline" placeholder="yyyy-MM-dd HH:mm"/>
	<acme:textbox code="finder.toDate" path="maxDeadline" placeholder="yyyy-MM-dd HH:mm"/>
	<br>
	<jstl:if test="${deadnul}">
		<spring:message code="finder.n.deadline" />
	</jstl:if>
	<br><br>
	<input type="submit" name="save" value="<spring:message code="finder.search" />" />
	<input type="submit" name="clear" value="<spring:message code="finder.clear" />" />
	<br>
	<br>
	<spring:message code="finder.results" />
	<br>
	
	
<display:table name="${finder.confereces}" id="row" requestURI="/finder/edit.do" pagesize="15" class="displaytag">
	<display:column property="title" titleKey="conference.title" />
	<display:column property="acronym" titleKey="conference.acronym" />
	<display:column property="startDate" titleKey="conference.startDate" />
	<display:column property="endDate" titleKey="conference.endDate" />
	
	<display:column>
		<acme:link url="position${rolURL}/display.do?conferenceId=${row.id}"
			code="conference.display" />
	</display:column>
	
	
</display:table>
		
</form:form>