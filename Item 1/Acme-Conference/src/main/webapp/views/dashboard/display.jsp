<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<table>
  <caption><spring:message code="SubmissionPerConference" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${SubmissionPerConferenceAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${SubmissionPerConferenceMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${SubmissionPerConferenceMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${SubmissionPerConferenceStd}"></jstl:out></td>
  </tr>
</table>

<table>
  <caption><spring:message code="RegistrationPerConference" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${RegistrationPerConferenceAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${RegistrationPerConferenceMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${RegistrationPerConferenceMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${RegistrationPerConferenceStd}"></jstl:out></td>
  </tr>
</table>


<table>
  <caption><spring:message code="ConferenceFees" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${ConferenceFeesAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${ConferenceFeesMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${ConferenceFeesMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${ConferenceFeesStd}"></jstl:out></td>
  </tr>
</table>


<table>
  <caption><spring:message code="CommentsPerActivity" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${CommentsPerActivityAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${CommentsPerActivityMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${CommentsPerActivityMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${CommentsPerActivityStd}"></jstl:out></td>
  </tr>
</table>

<table>
  <caption><spring:message code="DaysPerConference" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${DaysPerConferenceAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${DaysPerConferenceMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${DaysPerConferenceMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${DaysPerConferenceStd}"></jstl:out></td>
  </tr>
</table>


<table>
  <caption><spring:message code="ConferencesPerCategory" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${ConferencesPerCategoryAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${ConferencesPerCategoryMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${ConferencesPerCategoryMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${ConferencesPerCategoryStd}"></jstl:out></td>
  </tr>
</table>


<table>
  <caption><spring:message code="CommentsPerConference" /></caption>
  <tr>
  	<td><spring:message code="avg" /></td>
    <td><jstl:out value="${CommentsPerConferenceAvg}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.min" /></td>
    <td><jstl:out value="${CommentsPerConferenceMin}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="dashboard.max" /></td>
    <td><jstl:out value="${CommentsPerConferenceMax}"></jstl:out></td>
  </tr>
  <tr>
  	<td><spring:message code="std" /></td>
    <td><jstl:out value="${CommentsPerConferenceStd}"></jstl:out></td>
  </tr>
</table>
