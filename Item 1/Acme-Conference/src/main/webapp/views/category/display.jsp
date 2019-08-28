<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<acme:display code="category.titleEn" value="${category.titleEn}" />
	<acme:display code="category.titleEs" value="${category.titleEs}" />
	<jstl:if test="${lang eq 'en' }" >
    	<acme:display code="category.father" value="${category.father.titleEn}" />
    </jstl:if>
    <jstl:if test="${lang eq 'es' }" >
    	<acme:display code="category.father" value="${category.father.titleEs}" />
    </jstl:if>
    <display:table name="subcategories" id="row"
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
	
	

<jstl:choose>
    <jstl:when test="${category.titleEn eq 'CONFERENCE'}">
    </jstl:when>
    <jstl:otherwise>
    <input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="category.edit" />"
           onclick="relativeRedir('category/administrator/edit.do?categoryId=${category.id}');"/>
    <input type="button" class="btn btn-danger" name="delete"
           value="<spring:message code="category.delete" />"
           onclick="relativeRedir('category/administrator/delete.do?categoryId=${category.id}');"/>
    </jstl:otherwise>
</jstl:choose>     


<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="category.back" />"
           onclick="relativeRedir('category/administrator/list.do');"/>