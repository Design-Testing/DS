<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:message code="section.img" var="altern"/>
<acme:display code="section.title" value="${section.title}" />
<acme:display code="section.summary" value="${section.summary}" />

<style type="text/css">
div.gallery {
  margin: 5px;
  border: 1px solid #ccc;
  float: left;
  width: 180px;
}

div.gallery:hover {
  border: 1px solid #777;
}

div.gallery img {
  width: 100%;
  height: auto;
}

div.desc {
  padding: 15px;
  text-align: center;
}
div.galleryContainer{
	display: inline-block;
}
</style>

<h2>
<spring:message code="section.picture"/>
</h2>
<div class="galleryContainer">
	<jstl:forEach items="${section.pictures}" var="picture" varStatus="loop">
				<div class="gallery">
				  <a target="_blank" href="${picture}">
				    <img src="${picture}" alt="${altern}" width="600" height="400">
				  </a>
				</div>
	</jstl:forEach>
</div>
<br/>
<br/>
<br/>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${isDraft}">
		<acme:button url="section/delete.do?sectionId=${section.id}&tutorialId=${tutorialId}&conferenceId=${conferenceId}" name="delete" code="section.delete" />
	</jstl:if>
</security:authorize>

<acme:button url="section/list.do?tutorialId=${tutorialId}&conferenceId=${conferenceId}" name="back" code="section.back" />
<acme:button url="tutorial/display.do?tutorialId=${tutorialId}&conferenceId=${conferenceId}" name="tutorial" code="section.tutorial" />

<jstl:if test="${not empty msg}">
	<h3 style="color: red;">
		<spring:message code="${msg}" />
	</h3>
</jstl:if>