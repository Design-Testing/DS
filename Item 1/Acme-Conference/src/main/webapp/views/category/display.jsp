<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<acme:display code="category.titleEn" value="${category.titleEn}" />
	<acme:display code="category.titleEs" value="${category.titleEs}" />

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="category.edit" />"
           onclick="relativeRedir('category/administrator/edit.do?categoryId=${category.id}');"/>
           
<input type="button" class="btn btn-danger" name="delete"
           value="<spring:message code="category.delete" />"
           onclick="relativeRedir('category/administrator/delete.do?categoryId=${category.id}');"/>

<input type="button" class="btn btn-danger" name="back"
           value="<spring:message code="category.back" />"
           onclick="relativeRedir('category/administrator/list.do');"/>