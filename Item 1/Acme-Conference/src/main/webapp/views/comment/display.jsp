<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<acme:display code="comment.title" value="${comment.title}" />

<acme:display code="comment.text" value="${comment.text}" />

<acme:display code="comment.moment" value="${comment.moment}" />

<acme:display code="comment.author" value="${comment.author}" />

<jstl:if test="${not empty comment.conference}">
	<acme:display code="comment.conference" value="${comment.conference}" />
</jstl:if>
<jstl:if test="${not empty comment.presentation}">
	<acme:display code="comment.presentation" value="${comment.presentation}" />
</jstl:if>
<jstl:if test="${not empty comment.panel}">
	<acme:display code="comment.panel" value="${comment.panel}" />
</jstl:if>
<jstl:if test="${not empty comment.tutorial}">
	<acme:display code="comment.tutorial" value="${comment.tutorial}" />
</jstl:if>


<acme:button url="${lastURL}" name="back" code="comment.back" />




