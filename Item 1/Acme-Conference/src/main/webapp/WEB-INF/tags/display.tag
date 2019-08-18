<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="value" required="true"%>
<%@ attribute name="code" required="true"%>
<%@ attribute name="url" required="false"%>

<%-- Definition --%>

<spring:message code="${code}" />:
<jstl:choose>
<jstl:when test="${url == null}"><jstl:out value="${value}" /></jstl:when>
<jstl:otherwise><a href="${url}"><jstl:out value="${value}" /></a></jstl:otherwise>
</jstl:choose>
<br />