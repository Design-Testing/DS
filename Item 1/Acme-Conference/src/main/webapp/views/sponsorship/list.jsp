<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


 <input type="button" class="btn btn-danger" name="create"
           value="<spring:message code="sponsorship.create" />"
           onclick="relativeRedir('sponsorship/sponsor/create.do');"/>
<br><br>
<display:table name="sponsorships" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

	<display:column property="banner" titleKey="sponsorship.banner" />
	<display:column property="targetPage" titleKey="sponsorship.targetPage" />
			
		<display:column>
			<acme:button url="sponsorship/sponsor/display.do?sponsorshipId=${row.id}" name="display" code="sponsorship.display"/>
			<acme:button url="sponsorship/sponsor/delete.do?sponsorshipId=${row.id}" name="delete" code="sponsorship.delete"/>
		</display:column>
		
</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;"><spring:message code="${msg}"/></h3>
</jstl:if>