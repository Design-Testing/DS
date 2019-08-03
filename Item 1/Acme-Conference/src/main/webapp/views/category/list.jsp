<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${isAdministrator eq true }" >

 <input type="button" class="btn btn-danger" name="create"
           value="<spring:message code="category.create" />"
           onclick="relativeRedir('conference/administrator/create.do');"/>
</jstl:if>

<display:table name="categories" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

	<display:column property="titleEn" titleKey="category.title" />
			
		<display:column>
			<acme:button url="category/administrator/display.do?categoryId=${row.id}" name="display" code="category.display"/>
			<acme:button url="category/administrator/delete.do?categoryId=${row.id}" name="delete" code="category.delete"/>
		</display:column>
		
</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;"><spring:message code="${msg}"/></h3>
</jstl:if>