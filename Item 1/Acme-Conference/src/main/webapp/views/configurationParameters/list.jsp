<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="configurationParameters" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">

		<h2>Configuration Parameters</h2>
	<p>Banner: <acme:display></acme:display></p>
	

</display:table>

<input type="button" class="btn btn-danger" name="edit"
           value="<spring:message code="configurationParameters.edit" />"
           onclick="relativeRedir('configurationParameters/edit.do');"/>