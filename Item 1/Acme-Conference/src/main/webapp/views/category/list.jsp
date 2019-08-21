<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


 <input type="button" class="btn btn-danger" name="create"
           value="<spring:message code="category.create" />"
           onclick="relativeRedir('category/administrator/create.do');"/>

<display:table name="categories" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
			<jstl:if test="${lang eq 'en' }" >
				<display:column property="titleEn" titleKey="category.title" />
    		</jstl:if>
    		<jstl:if test="${lang eq 'es' }" >
    			<display:column property="titleEs" titleKey="category.title" />
    		</jstl:if>
			
		<display:column>
			<acme:button url="category/administrator/display.do?categoryId=${row.id}" name="display" code="category.display"/>
		</display:column>
		
</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;"><spring:message code="${msg}"/></h3>
</jstl:if>