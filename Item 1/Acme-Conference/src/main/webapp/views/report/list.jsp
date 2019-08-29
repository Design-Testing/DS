<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="reports" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="submission.ticker" titleKey="report.submission.ticker" />
	<display:column titleKey="report.decision">
		<jstl:choose>
			<jstl:when test="${row.isDraft}">
				<spring:message code="report.isDraft" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="report.final" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	
	<display:column>
		<acme:button url="comment/list.do?entity=report&id=${row.id}"
			name="list" code="comments" />
	</display:column>
	
	<security:authorize access="hasRole('REVIEWER')">
		<display:column>
			<jstl:if test="${row.isDraft}">
				<button onclick="location.href = 'report/reviewer/edit.do?reportId=${row.id}'">
					<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAMAAADXqc3KAAAAUVBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA3C4bvAAAAGnRSTlMAlZY5fFwTgH9X/PvwQIjvP1vVvsDx9BEVPrZ1NJEAAABgSURBVCjPvZFJEoAgDAQDAnHHfcn/H6rgkeFk6Ry7s1QlRO9j2gpyFqltRkiT9gyP8Wk5R2PAmGAYcJEerg3pMrz8mZPTkXN6oQNyWxDtGtSr9TYOzFlkO+FvplmN9GEuZT4JYKeoDlYAAAAASUVORK5CYII=">
				</button>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.isDraft}">
				<acme:button
					url="comment/create.do?entityId=${row.id}&entity=report"
					name="new" code="add.comment" />
			</jstl:if>
		</display:column>
		<display:column>
			<button onclick="location.href = 'report/reviewer/display.do?reportId=${row.id}'">
				<img width="16px" height="16px"src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABmJLR0QA/wD/AP+gvaeTAAAFYUlEQVRo3u1Xa2hbZRjONp2wzgki8wIqoiKI4BCdtz/7I/vhnw0UEQc6tJtTVFBRtjkZqDDQrunqwO4i2iRtOTM56dp1dnZNbzZrm3ub5iTnJE2bJmmbprk198vne9IEm3NObrbbipwXHgrt1+993vd73uf7jkDABx988MEHH3z8D6KuVf6wUCyrBZwDKIViqQ1+LgG8AGu9SNYnlEgbhWL8wE8tHfdtCNJnMWx7gwQ/JJTgY0ASVYGUUCK7DkW+dRLDtt4W4vVi/CgQWaySOAfwaaFIeripqenOW0Ke7hoknl07cRaM9SJ8z00jfgrD7oFutZXoJLqId4euDIzYxi1TtgWffzEWT8TCkVhkzuvzqAmKkiuG7eelXZESRWQaJLK6dZfVaQm+mx5ErqQ/t8ozl/uUU75gyIcqDLfHN3/pr/7ZBomcuxCYqdOi9ifWhXyDSHYQNk0wk8DwIppEKBIN5ollAB0uL3p3jEBP/jmKtuGD6G75EHr62hg6oiHRgMdfUIjHF/BIunrni5xGQCiSvbY2vUvwb+hjZW5+QdYVdi4sOleT0fmX0QvXNUjwR39J7B00oOlwrKAQy7TT9gvWmeAoIl4vkb1TNXHYc9OKZ7M7I+0ZcKRSqcRqAp1uL6qBTpcjn8f9HUo0thQsKALmJVrkNDJguZ9WJxsYJOZGZ1pwpJ4kLUw9q32hrFQqJZ/Hg51K5IjEWPPRo9RQcPLsIsBqK9S89AST/Nm29ozZPmNlJkuD6Hf1qKsmn8e+4QnOIR+dICwNLawBT4PNvl1G87I3mZoH8mnQu4MrUZtjgZtccycSfH0SCd7/EAlqP0KCY98jgaiLc61qKcRZxAQ1RZ1hn0RU2Cx/kZN8vQTfBQvCBRYJnZ9xz08Xs8P9w0Y2qYuyFeLvfVCIw59wFvGVwVbUbrVmiqsIV2Nr+0MF5H9s7q6BP5iYNmmyTdtK+fnOjmF2AV8cZ5PP48Qp1vqXFdqSd4ZSP2nmGOx+DMO2/Nt9Md7MXKQY1VlKbRyHAdjEJI/1AtHa4gUc+YxVwCNdN8pefJd7/7az7iGYVZaMtAT1KiAJyCz6AyV3TkABm6VrL+DRrpGyBcy4F/qAEwL49QTxWMlB1pqoL1cWW5eXo1Giagl9XkJC37Il9EoZCXn9wVHgkwKkYSb2VWSlsPh8rmJPJBa3Ftv8DWWVQyy+ylp/dLz4mAWXwwa6kVkuJvJYxRcZSZJ36Qiqh/5HnZlyRuNxTifCitnor3ihjR7/gdOB6BnS+EJFyQOHYJYDYb1Avw6quo1VKtc2rcnan63eTC2AnExcF9lza7jI6BPkCl9oWQN5QzkpX8IQ2vKfHnM3SHIHbNKbk1PAF1hWM5PRj7hq3kHlnhJuz9Ig5ErkGvebQqG4Y00vUqPRuBU2askVkaEcrj5ofGp10qtzXrS9iiIeAPJqhnTSmUyUnHH25/KAbKgG+PXmdfkmoDfSENR3WSeAzSfIKQ1zLgxwEi/1asuSf31onNX5UDg6qbNYLTnyEZ2JOnRTPivBCfZAAmcuUYLuWCqdLviguQJP64MqM3qqe+WDZkf7EHrmmgp9rCXR0GKggHgylfLmup5a6Tpp0phtz97Uj3o9Re2EwRLRcsq51LzdvaCgyVT6SRmLJ2dtDpci7zKAuMZkraONQ3CrQkNYn4fE+RsyeyLjlF3lmPP0gQXqE8nUHK3rdDoTjieTbnAWtd01pzCQNkNeinmXUZHk44LbFToTuReIyHNPEFQhInDj/64z23YLNkoYjY57QcMHgNg5LUGOgDzcQDQGCANmwQQGNGaqESS3X6/X12wY4nzwwQcffPDBxxriH8n/fy/eFiJ1AAAAAElFTkSuQmCC">
			</button>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('AUTHOR')">
		<display:column>
			<button onclick="location.href = 'report/author/display.do?reportId=${row.id}'">
				<img width="16px" height="16px"src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABmJLR0QA/wD/AP+gvaeTAAAFYUlEQVRo3u1Xa2hbZRjONp2wzgki8wIqoiKI4BCdtz/7I/vhnw0UEQc6tJtTVFBRtjkZqDDQrunqwO4i2iRtOTM56dp1dnZNbzZrm3ub5iTnJE2bJmmbprk198vne9IEm3NObrbbipwXHgrt1+993vd73uf7jkDABx988MEHH3z8D6KuVf6wUCyrBZwDKIViqQ1+LgG8AGu9SNYnlEgbhWL8wE8tHfdtCNJnMWx7gwQ/JJTgY0ASVYGUUCK7DkW+dRLDtt4W4vVi/CgQWaySOAfwaaFIeripqenOW0Ke7hoknl07cRaM9SJ8z00jfgrD7oFutZXoJLqId4euDIzYxi1TtgWffzEWT8TCkVhkzuvzqAmKkiuG7eelXZESRWQaJLK6dZfVaQm+mx5ErqQ/t8ozl/uUU75gyIcqDLfHN3/pr/7ZBomcuxCYqdOi9ifWhXyDSHYQNk0wk8DwIppEKBIN5ollAB0uL3p3jEBP/jmKtuGD6G75EHr62hg6oiHRgMdfUIjHF/BIunrni5xGQCiSvbY2vUvwb+hjZW5+QdYVdi4sOleT0fmX0QvXNUjwR39J7B00oOlwrKAQy7TT9gvWmeAoIl4vkb1TNXHYc9OKZ7M7I+0ZcKRSqcRqAp1uL6qBTpcjn8f9HUo0thQsKALmJVrkNDJguZ9WJxsYJOZGZ1pwpJ4kLUw9q32hrFQqJZ/Hg51K5IjEWPPRo9RQcPLsIsBqK9S89AST/Nm29ozZPmNlJkuD6Hf1qKsmn8e+4QnOIR+dICwNLawBT4PNvl1G87I3mZoH8mnQu4MrUZtjgZtccycSfH0SCd7/EAlqP0KCY98jgaiLc61qKcRZxAQ1RZ1hn0RU2Cx/kZN8vQTfBQvCBRYJnZ9xz08Xs8P9w0Y2qYuyFeLvfVCIw59wFvGVwVbUbrVmiqsIV2Nr+0MF5H9s7q6BP5iYNmmyTdtK+fnOjmF2AV8cZ5PP48Qp1vqXFdqSd4ZSP2nmGOx+DMO2/Nt9Md7MXKQY1VlKbRyHAdjEJI/1AtHa4gUc+YxVwCNdN8pefJd7/7az7iGYVZaMtAT1KiAJyCz6AyV3TkABm6VrL+DRrpGyBcy4F/qAEwL49QTxWMlB1pqoL1cWW5eXo1Giagl9XkJC37Il9EoZCXn9wVHgkwKkYSb2VWSlsPh8rmJPJBa3Ftv8DWWVQyy+ylp/dLz4mAWXwwa6kVkuJvJYxRcZSZJ36Qiqh/5HnZlyRuNxTifCitnor3ihjR7/gdOB6BnS+EJFyQOHYJYDYb1Avw6quo1VKtc2rcnan63eTC2AnExcF9lza7jI6BPkCl9oWQN5QzkpX8IQ2vKfHnM3SHIHbNKbk1PAF1hWM5PRj7hq3kHlnhJuz9Ig5ErkGvebQqG4Y00vUqPRuBU2askVkaEcrj5ofGp10qtzXrS9iiIeAPJqhnTSmUyUnHH25/KAbKgG+PXmdfkmoDfSENR3WSeAzSfIKQ1zLgxwEi/1asuSf31onNX5UDg6qbNYLTnyEZ2JOnRTPivBCfZAAmcuUYLuWCqdLviguQJP64MqM3qqe+WDZkf7EHrmmgp9rCXR0GKggHgylfLmup5a6Tpp0phtz97Uj3o9Re2EwRLRcsq51LzdvaCgyVT6SRmLJ2dtDpci7zKAuMZkraONQ3CrQkNYn4fE+RsyeyLjlF3lmPP0gQXqE8nUHK3rdDoTjieTbnAWtd01pzCQNkNeinmXUZHk44LbFToTuReIyHNPEFQhInDj/64z23YLNkoYjY57QcMHgNg5LUGOgDzcQDQGCANmwQQGNGaqESS3X6/X12wY4nzwwQcffPDBxxriH8n/fy/eFiJ1AAAAAElFTkSuQmCC">
			</button>
		</display:column>
	</security:authorize>

</display:table>
