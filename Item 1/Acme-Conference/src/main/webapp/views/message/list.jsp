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
function deleteMessage(id, text){
	var confirma = confirm(text);
	if(confirma){
		window.location.href = "message/delete.do?messageId=" + id;
	}
}
</script>


<button onclick="location.href = 'message/create.do'">
	<spring:message code="new" />
</button>
<display:table name="messages" id="row"
		requestURI="${requestURI}" pagesize="5"
		class="displaytag">
		
	
	<display:column property="sender.name" titleKey="sender" />
	<display:column property="subject" titleKey="subject" />
	
	<display:column>
		<button onclick="deleteMessage(${row.id}, '<spring:message code="delete.alert" />')">
			<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAMAAABg3Am1AAACAVBMVEVHcEy73/y83vu73vq73vu73vu43vrE2P94kJx+mKW73vu73fu73vu63/q73fu83/u73/u83fyVscR/mKd4kJyVr8V4kZx4kJx5kZ273vuBmai63vu73vq83vm73vu73vu63vu73f+83vu63fy73fm83vu73vu73vu63vy73/q22/+53Pm56P+73vu53vy73fu83fq84fm+2/m73/u83/q63/q/3/+73vu83/x4kJzj8v273vsVZcAeiOXh8f3H5PvW6/3b7vza7vzR6fzM5vzB4fy83/vQ6PzG4/u93vvV6/3g8P3f8P15kJx5kp4eh+XB4vzC4fy93/vL5/wXZsArj+YukedFhs8gieU0lOiHwPIsj+ewz+0mjOZTpevN4/Y+idUbftrO5fltq+eiy+8kb8VPk9gfhuKs0vRqse4XbckWacTQ6fwxk+cwkucxi+B1tu/M5/wYcMzg8Pwxk+jL5vyXyfSWyPTZ7fzZ7PzC4vyr1Pcdh+UncsXD4PlJic+Ot+Q/mulyte+s1fd6uvBxo9vd7vzO4/fS5vg7f8xBg80sj+a41fDY7PvP5/qBvfHW6/uIwfIlcMWXvucWZsBcltUZd9M2fc3R5fcZZ8Fonti71vHc7vxGnumEv/Eji+ZOoutBg841lOiQueXf7/x+vPHB2vLM5fqn0vbY7fyk0PYHaVzaAAAAOXRSTlMAT+5sz6wvDe78i6/sb4/MrUx9+8R2wpOUy/qNbS7+yqsPjEotqu3OTnAOLAuKTc1qKiuObjAQ6lDqe/IQAAACE0lEQVRIx+XV51tTMRQH4MtGNipuBRVx4173lxRaKC1l7ynuvfcWBRS34kBERXHrX+nNTVvszbnpF79xPuXJyfs0yenJNYzpG7N3xY05MSA9PkiPAfPq4q2vmxu7p/nwmZrwItlxiCKU60A5Mh0gAwEdqECOAyShUgcqke0A+QjqQBkSHGAtmnSgEakOkIYqHWjGUmets1Djvr4WicqfI1dXCC9WKiDln0LcuXX+Jue7f0UnQshTwDKcjKSftDI2yjn/8vtDtAwLFbAILeGs5xFj7G0P558+fgtPtWCrAlahLJwdfsjY696Jzi7+faoMpQpYDH8k7Xn18o3553Nf38/IjB/FCogU4tnz7u6nD8ak/DouQTXS1KYrQa3IvWh7zEfksh+TjPWKQQ2yiC7dAK9IDlj32X7fBh3W4fvFwIdcAuQhJJLW7fCh2zZ4/46xQ7IbUgiwBRUieZbzuxfsAwya99iJw2IYQAYBsnFOJPcfaThubcU0r7RZv9VwUHZDEgESwoU4sI8JcO10uwXO2FNB5BMgFY3yLvfa4OrFUxY4ak81YTMB1qBa3uaejlaxpctdvPOYpgyGkRjbEZ4bU92wg3wuk2Uh1PChkASZbk9TCCtIkCMLoUYA60mw2u1puoSNJNgZ7QhHBLGcBNtwnQZ+bCLBdjTToGrJOvoz5PptqDf+E5jhBha4gJkuYlaBMc3jL0cbGzfaWQZWAAAAAElFTkSuQmCC">
		</button>
	</display:column>
		
	<display:column>
		<button onclick="location.href = 'topic/display.do?messageId=${row.id}'">
			<img width="16px" height="16px" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAMAAADXqc3KAAAAUVBMVEVHcEwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA3C4bvAAAAGnRSTlMAlZY5fFwTgH9X/PvwQIjvP1vVvsDx9BEVPrZ1NJEAAABgSURBVCjPvZFJEoAgDAQDAnHHfcn/H6rgkeFk6Ry7s1QlRO9j2gpyFqltRkiT9gyP8Wk5R2PAmGAYcJEerg3pMrz8mZPTkXN6oQNyWxDtGtSr9TYOzFlkO+FvplmN9GEuZT4JYKeoDlYAAAAASUVORK5CYII=">
		</button>
	</display:column>

</display:table>
