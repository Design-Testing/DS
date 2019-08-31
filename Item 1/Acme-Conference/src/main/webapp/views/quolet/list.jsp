<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${not empty rol}">
	<jstl:set var="rolURL" value="/${rol}" />
</jstl:if>


<display:table name="quolets" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

	<display:column property="ticker" titleKey="quolet.ticker" />

	<security:authorize access="hasRole('COMPANY')">		
		<display:column>
			<acme:button url="quolet/company/edit.do?quoletId=${row.id}" name="display" code="quolet.edit"/>
		</display:column>
		<display:column>
			<acme:button url="quolet/company/delete.do?quoletId=${row.id}" name="delete" code="quolet.delete"/>
		</display:column>
	</security:authorize>
	
	<display:column>
	<jstl:choose>
		<jstl:when test="${rol eq 'company'}">
			<acme:button url="quolet/company/display.do?quoletId=${row.id}" name="display" code="quolet.display"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:button url="quolet/rooky/display.do?quoletId=${row.id}" name="display" code="quolet.display"/>
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
	
</display:table>




