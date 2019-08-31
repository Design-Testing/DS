<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<acme:display code="audit.position" value="${audit.position.title}"/>

<acme:display code="audit.auditor" value="${audit.auditor.name}"/>

<acme:display code="audit.text" value="${audit.text}"/>

<acme:display code="audit.score" value="${audit.score}"/>



<jstl:choose>
	<jstl:when test="${lang eq 'en' }">
		<spring:message code="audit.moment" />: <fmt:formatDate
			value="${audit.moment}" type="both" pattern="yyyy/MM/dd HH:mm" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="audit.moment" />: <fmt:formatDate
			value="${audit.moment}" type="both" pattern="dd-MM-yyyy HH:mm" />
	</jstl:otherwise>
</jstl:choose>


<br><br>

<input type="button" name="dsiplay"
                value="<spring:message code="audit.position.display" />"
                onclick="relativeRedir('position/display.do?positionId=${audit.position.id}')" />


<br><br>


<security:authorize access="hasRole('AUDITOR')">
	<jstl:if test="${audit.isDraft eq true }">
            <jstl:set var="toListDraft" value="1"/>
	</jstl:if>	
	<jstl:if test="${audit.isDraft eq false }">
            <jstl:set var="toListFinal" value="1"/>
	</jstl:if>
</security:authorize>

	
<jstl:choose>
	<jstl:when test="${toListDraft eq 1}">
		<acme:button url="audit/auditor/listDraft.do" name="back"
		code="audit.back" />
	</jstl:when>
	<jstl:when test="${toListFinal eq 1}">
		<acme:button url="audit/auditor/listFinal.do" name="back"
		code="audit.back" />
	</jstl:when>
	<jstl:otherwise>
		<acme:button url="position/list.do" name="back" 
		code="audit.back"/>
	</jstl:otherwise>
</jstl:choose>

<jstl:if test="${areQuoletsMineAuditor or areQuoletsMineCompany}">

<h3><spring:message code="audit.quolets"/></h3>

<security:authorize access="hasRole('COMPANY')">
	<jstl:if test="${audit.isDraft eq false and areQuoletsMineCompany}">
		<acme:button url="quolet/company/create.do?auditId=${audit.id}" name="create" code="quolet.create"/>
	</jstl:if>
</security:authorize>
<display:table name="quolets" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate var="strigActualDateTime" value="${now}" pattern="yyyy/MM/dd HH:mm" />
	<fmt:parseDate value="${strigActualDateTime}" pattern="yyyy/MM/dd HH:mm" var="actualDateTime" />
	<jstl:set var="actualDateTimeInMin" value="${actualDateTime.time /60000 }"/>
	<jstl:set var="oneMonthInMin" value="${1*30*24*60}"/>
	<jstl:set var="momentInMin" value="${row.moment.time /60000 }"/>
	<jstl:set var="differenceInMin" value="${actualDateTimeInMin - momentInMin }"/>
	
	<jstl:choose>
	<jstl:when test="${differenceInMin < oneMonthInMin}" >
		<display:column property="ticker" titleKey="quolet.ticker" style="background-color: Indigo;"/>
	</jstl:when>
	<jstl:when test="${(differenceInMin > oneMonthInMin) and (differenceInMin < oneMonthInMin*2)}" >
		<display:column property="ticker" titleKey="quolet.ticker" style="background-color: DarkSlateGrey;"/>
	</jstl:when>
	<jstl:otherwise>
		<display:column property="ticker" titleKey="quolet.ticker" style="background-color: PapayaWhip;"/>
	</jstl:otherwise>
	</jstl:choose>
	
	

	
	<security:authorize access="hasRole('COMPANY')">		
		
		<display:column>
			<jstl:if test="${row.isDraft and areQuoletsMineCompany}">
				<acme:button url="quolet/company/edit.do?quoletId=${row.id}&auditId=${row.audit.id}" name="display" code="quolet.edit"/>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.isDraft and areQuoletsMineCompany}">
				<acme:button url="quolet/company/delete.do?quoletId=${row.id}" name="delete" code="quolet.delete"/>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.isDraft and areQuoletsMineCompany}">
				<acme:button url="quolet/company/finalMode.do?quoletId=${row.id}" name="finalMode" code="quolet.finalMode"/>
			</jstl:if>
		</display:column>
		
	</security:authorize>
	
	
	
	<security:authorize access="hasRole('COMPANY')">	
		<jstl:if test="${row.isDraft eq false}">
		<display:column>	
		<acme:button url="quolet/company/display.do?quoletId=${row.id}" name="display" code="quolet.display"/>
		</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('AUDITOR')">
		<jstl:if test="${row.isDraft eq true}">
		<display:column>	
			<jstl:out value="No final"/>
		</display:column>
		</jstl:if>
		<jstl:if test="${row.isDraft eq false}">
		<display:column>	
		<acme:button url="quolet/auditor/display.do?quoletId=${row.id}" name="display" code="quolet.display"/>
		</display:column>
		</jstl:if>
	</security:authorize>
	
	
</display:table>

<h4 style="color: Indigo "><spring:message code="audit.one.quolet"/> Indigo</h4>
<h4 style="color: DarkSlateGrey "><spring:message code="audit.two.quolet"/> DarkSlateGrey</h4>
<h4 style="color: PapayaWhip "><spring:message code="audit.more.quolet"/> PapayaWhip</h4>


</jstl:if>



