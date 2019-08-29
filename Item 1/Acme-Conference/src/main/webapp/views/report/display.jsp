<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<acme:display code="report.originality" value="${report.originality}"/>
<acme:display code="report.quality" value="${report.quality}"/>
<acme:display code="report.readability" value="${report.readability}"/>
<acme:display code="report.decision" value="${report.decision}"/>
<acme:button code="report.listComment" name="list" url="comment/list.do?entity=report&id=${report.id}"/>

