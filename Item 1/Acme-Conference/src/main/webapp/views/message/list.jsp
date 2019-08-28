<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function deleteMessage(id, text, origen, entityId){
	var confirma = confirm(text);
	if(confirma){
		if(origen=="topic" || origen=="sender" || origen=="recipient"){
			window.location.href = "message/delete.do?messageId=" + id + "&origen="+ origen + "&entityId=" + entityId;
		} else {
			window.location.href = "message/delete.do?messageId=" + id + "&origen="+ origen;
		}
	}
}
</script>

<jstl:choose>
	<jstl:when test="${not empty entityId}">
		<acme:button url="message/create.do?origen=${origen}&entityId=${entityId}" name="new" code="new"/>
	</jstl:when>
	<jstl:otherwise>
		<acme:button url="message/create.do?origen=${origen}" name="new" code="new"/>
	</jstl:otherwise>
</jstl:choose>

<br>

<form action="message/listByTopic.do" method="get">
	<select name="topicId">
		<jstl:forEach items="${topics}" var="rt">
			<jstl:choose>
				<jstl:when test="${lang eq 'en' }">
					<option value="${rt.id}" >${rt.english}</option>
				</jstl:when>
				<jstl:otherwise>
					<option value="${rt.id}">${rt.spanish}</option>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</select>
	
	<button name="share" type="submit" class="button">
		<spring:message code="listByTopic" />
	</button>
</form>

<form action="message/listBySender.do" method="get">
	<select name="actorId">
		<jstl:forEach items="${actors}" var="ra">
				<option value="${ra.id}" >${ra.name}</option>
		</jstl:forEach>
	</select>
	
	<button type="submit" class="button">
		<spring:message code="listBySender" />
	</button>
	<button type="submit" formaction="message/listByRecipient.do" class="button">
		<spring:message code="listByRecipient" />
	</button>
</form>


<display:table name="messages" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="sender.name" titleKey="sender" />
	<display:column property="subject" titleKey="subject" />
	
	<display:column>
		<jstl:choose>
			<jstl:when test="${not empty entityId}">
				<button onclick="deleteMessage(${row.id}, '<spring:message code="delete.alert" />', '${origen}', ${entityId})">
					<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAACAVBMVEVHcEy73/y83vu73vq73vu73vu43vrE2P94kJx+mKW73vu73fu73vu63/q73fu83/u73/u83fyVscR/mKd4kJyVr8V4kZx4kJx5kZ273vuBmai63vu73vq83vm73vu73vu63vu73f+83vu63fy73fm83vu73vu73vu63vy73/q22/+53Pm56P+73vu53vy73fu83fq84fm+2/m73/u83/q63/q/3/+73vu83/x4kJzj8v273vsVZcAeiOXh8f3H5PvW6/3b7vza7vzR6fzM5vzB4fy83/vQ6PzG4/u93vvV6/3g8P3f8P15kJx5kp4eh+XB4vzC4fy93/vL5/wXZsArj+YukedFhs8gieU0lOiHwPIsj+ewz+0mjOZTpevN4/Y+idUbftrO5fltq+eiy+8kb8VPk9gfhuKs0vRqse4XbckWacTQ6fwxk+cwkucxi+B1tu/M5/wYcMzg8Pwxk+jL5vyXyfSWyPTZ7fzZ7PzC4vyr1Pcdh+UncsXD4PlJic+Ot+Q/mulyte+s1fd6uvBxo9vd7vzO4/fS5vg7f8xBg80sj+a41fDY7PvP5/qBvfHW6/uIwfIlcMWXvucWZsBcltUZd9M2fc3R5fcZZ8Fonti71vHc7vxGnumEv/Eji+ZOoutBg841lOiQueXf7/x+vPHB2vLM5fqn0vbY7fyk0PYHaVzaAAAAOXRSTlMAT+5sz6wvDe78i6/sb4/MrUx9+8R2wpOUy/qNbS7+yqsPjEotqu3OTnAOLAuKTc1qKiuObjAQ6lDqe/IQAAACE0lEQVRIx+XV51tTMRQH4MtGNipuBRVx4173lxRaKC1l7ynuvfcWBRS34kBERXHrX+nNTVvszbnpF79xPuXJyfs0yenJNYzpG7N3xY05MSA9PkiPAfPq4q2vmxu7p/nwmZrwItlxiCKU60A5Mh0gAwEdqECOAyShUgcqke0A+QjqQBkSHGAtmnSgEakOkIYqHWjGUmets1Djvr4WicqfI1dXCC9WKiDln0LcuXX+Jue7f0UnQshTwDKcjKSftDI2yjn/8vtDtAwLFbAILeGs5xFj7G0P558+fgtPtWCrAlahLJwdfsjY696Jzi7+faoMpQpYDH8k7Xn18o3553Nf38/IjB/FCogU4tnz7u6nD8ak/DouQTXS1KYrQa3IvWh7zEfksh+TjPWKQQ2yiC7dAK9IDlj32X7fBh3W4fvFwIdcAuQhJJLW7fCh2zZ4/46xQ7IbUgiwBRUieZbzuxfsAwya99iJw2IYQAYBsnFOJPcfaThubcU0r7RZv9VwUHZDEgESwoU4sI8JcO10uwXO2FNB5BMgFY3yLvfa4OrFUxY4ak81YTMB1qBa3uaejlaxpctdvPOYpgyGkRjbEZ4bU92wg3wuk2Uh1PChkASZbk9TCCtIkCMLoUYA60mw2u1puoSNJNgZ7QhHBLGcBNtwnQZ+bCLBdjTToGrJOvoz5PptqDf+E5jhBha4gJkuYlaBMc3jL0cbGzfaWQZWAAAAAElFTkSuQmCC">
				</button>
			</jstl:when>
			<jstl:otherwise>
				<button onclick="deleteMessage(${row.id}, '<spring:message code="delete.alert" />', '${origen}', 'noId')">
					<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAACAVBMVEVHcEy73/y83vu73vq73vu73vu43vrE2P94kJx+mKW73vu73fu73vu63/q73fu83/u73/u83fyVscR/mKd4kJyVr8V4kZx4kJx5kZ273vuBmai63vu73vq83vm73vu73vu63vu73f+83vu63fy73fm83vu73vu73vu63vy73/q22/+53Pm56P+73vu53vy73fu83fq84fm+2/m73/u83/q63/q/3/+73vu83/x4kJzj8v273vsVZcAeiOXh8f3H5PvW6/3b7vza7vzR6fzM5vzB4fy83/vQ6PzG4/u93vvV6/3g8P3f8P15kJx5kp4eh+XB4vzC4fy93/vL5/wXZsArj+YukedFhs8gieU0lOiHwPIsj+ewz+0mjOZTpevN4/Y+idUbftrO5fltq+eiy+8kb8VPk9gfhuKs0vRqse4XbckWacTQ6fwxk+cwkucxi+B1tu/M5/wYcMzg8Pwxk+jL5vyXyfSWyPTZ7fzZ7PzC4vyr1Pcdh+UncsXD4PlJic+Ot+Q/mulyte+s1fd6uvBxo9vd7vzO4/fS5vg7f8xBg80sj+a41fDY7PvP5/qBvfHW6/uIwfIlcMWXvucWZsBcltUZd9M2fc3R5fcZZ8Fonti71vHc7vxGnumEv/Eji+ZOoutBg841lOiQueXf7/x+vPHB2vLM5fqn0vbY7fyk0PYHaVzaAAAAOXRSTlMAT+5sz6wvDe78i6/sb4/MrUx9+8R2wpOUy/qNbS7+yqsPjEotqu3OTnAOLAuKTc1qKiuObjAQ6lDqe/IQAAACE0lEQVRIx+XV51tTMRQH4MtGNipuBRVx4173lxRaKC1l7ynuvfcWBRS34kBERXHrX+nNTVvszbnpF79xPuXJyfs0yenJNYzpG7N3xY05MSA9PkiPAfPq4q2vmxu7p/nwmZrwItlxiCKU60A5Mh0gAwEdqECOAyShUgcqke0A+QjqQBkSHGAtmnSgEakOkIYqHWjGUmets1Djvr4WicqfI1dXCC9WKiDln0LcuXX+Jue7f0UnQshTwDKcjKSftDI2yjn/8vtDtAwLFbAILeGs5xFj7G0P558+fgtPtWCrAlahLJwdfsjY696Jzi7+faoMpQpYDH8k7Xn18o3553Nf38/IjB/FCogU4tnz7u6nD8ak/DouQTXS1KYrQa3IvWh7zEfksh+TjPWKQQ2yiC7dAK9IDlj32X7fBh3W4fvFwIdcAuQhJJLW7fCh2zZ4/46xQ7IbUgiwBRUieZbzuxfsAwya99iJw2IYQAYBsnFOJPcfaThubcU0r7RZv9VwUHZDEgESwoU4sI8JcO10uwXO2FNB5BMgFY3yLvfa4OrFUxY4ak81YTMB1qBa3uaejlaxpctdvPOYpgyGkRjbEZ4bU92wg3wuk2Uh1PChkASZbk9TCCtIkCMLoUYA60mw2u1puoSNJNgZ7QhHBLGcBNtwnQZ+bCLBdjTToGrJOvoz5PptqDf+E5jhBha4gJkuYlaBMc3jL0cbGzfaWQZWAAAAAElFTkSuQmCC">
				</button>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
		
	<display:column>
		<button onclick="location.href = 'message/display.do?messageId=${row.id}'">
			<img width="16px" height="16px"src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABmJLR0QA/wD/AP+gvaeTAAAFYUlEQVRo3u1Xa2hbZRjONp2wzgki8wIqoiKI4BCdtz/7I/vhnw0UEQc6tJtTVFBRtjkZqDDQrunqwO4i2iRtOTM56dp1dnZNbzZrm3ub5iTnJE2bJmmbprk198vne9IEm3NObrbbipwXHgrt1+993vd73uf7jkDABx988MEHH3z8D6KuVf6wUCyrBZwDKIViqQ1+LgG8AGu9SNYnlEgbhWL8wE8tHfdtCNJnMWx7gwQ/JJTgY0ASVYGUUCK7DkW+dRLDtt4W4vVi/CgQWaySOAfwaaFIeripqenOW0Ke7hoknl07cRaM9SJ8z00jfgrD7oFutZXoJLqId4euDIzYxi1TtgWffzEWT8TCkVhkzuvzqAmKkiuG7eelXZESRWQaJLK6dZfVaQm+mx5ErqQ/t8ozl/uUU75gyIcqDLfHN3/pr/7ZBomcuxCYqdOi9ifWhXyDSHYQNk0wk8DwIppEKBIN5ollAB0uL3p3jEBP/jmKtuGD6G75EHr62hg6oiHRgMdfUIjHF/BIunrni5xGQCiSvbY2vUvwb+hjZW5+QdYVdi4sOleT0fmX0QvXNUjwR39J7B00oOlwrKAQy7TT9gvWmeAoIl4vkb1TNXHYc9OKZ7M7I+0ZcKRSqcRqAp1uL6qBTpcjn8f9HUo0thQsKALmJVrkNDJguZ9WJxsYJOZGZ1pwpJ4kLUw9q32hrFQqJZ/Hg51K5IjEWPPRo9RQcPLsIsBqK9S89AST/Nm29ozZPmNlJkuD6Hf1qKsmn8e+4QnOIR+dICwNLawBT4PNvl1G87I3mZoH8mnQu4MrUZtjgZtccycSfH0SCd7/EAlqP0KCY98jgaiLc61qKcRZxAQ1RZ1hn0RU2Cx/kZN8vQTfBQvCBRYJnZ9xz08Xs8P9w0Y2qYuyFeLvfVCIw59wFvGVwVbUbrVmiqsIV2Nr+0MF5H9s7q6BP5iYNmmyTdtK+fnOjmF2AV8cZ5PP48Qp1vqXFdqSd4ZSP2nmGOx+DMO2/Nt9Md7MXKQY1VlKbRyHAdjEJI/1AtHa4gUc+YxVwCNdN8pefJd7/7az7iGYVZaMtAT1KiAJyCz6AyV3TkABm6VrL+DRrpGyBcy4F/qAEwL49QTxWMlB1pqoL1cWW5eXo1Giagl9XkJC37Il9EoZCXn9wVHgkwKkYSb2VWSlsPh8rmJPJBa3Ftv8DWWVQyy+ylp/dLz4mAWXwwa6kVkuJvJYxRcZSZJ36Qiqh/5HnZlyRuNxTifCitnor3ihjR7/gdOB6BnS+EJFyQOHYJYDYb1Avw6quo1VKtc2rcnan63eTC2AnExcF9lza7jI6BPkCl9oWQN5QzkpX8IQ2vKfHnM3SHIHbNKbk1PAF1hWM5PRj7hq3kHlnhJuz9Ig5ErkGvebQqG4Y00vUqPRuBU2askVkaEcrj5ofGp10qtzXrS9iiIeAPJqhnTSmUyUnHH25/KAbKgG+PXmdfkmoDfSENR3WSeAzSfIKQ1zLgxwEi/1asuSf31onNX5UDg6qbNYLTnyEZ2JOnRTPivBCfZAAmcuUYLuWCqdLviguQJP64MqM3qqe+WDZkf7EHrmmgp9rCXR0GKggHgylfLmup5a6Tpp0phtz97Uj3o9Re2EwRLRcsq51LzdvaCgyVT6SRmLJ2dtDpci7zKAuMZkraONQ3CrQkNYn4fE+RsyeyLjlF3lmPP0gQXqE8nUHK3rdDoTjieTbnAWtd01pzCQNkNeinmXUZHk44LbFToTuReIyHNPEFQhInDj/64z23YLNkoYjY57QcMHgNg5LUGOgDzcQDQGCANmwQQGNGaqESS3X6/X12wY4nzwwQcffPDBxxriH8n/fy/eFiJ1AAAAAElFTkSuQmCC">
		</button>
	</display:column>

</display:table>

<jstl:if test="${not empty msg}">
	<h3 style="color: red;">
		<spring:message code="${msg}" />
	</h3>
</jstl:if>
