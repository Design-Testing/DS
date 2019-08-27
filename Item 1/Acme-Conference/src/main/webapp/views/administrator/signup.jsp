<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script>
	function phoneFun() {
		var x = document.getElementById("phone");
		var telefono = x.value;
		var CCACPN = new RegExp("(^\\+([1-9]{1}[0-9]{0,2})){1}\\s*(\\([1-9]{1}[0-9]{0,2}\\)){1}\\s*(\\d{4,}$)"); /* +CC (AC) PN */
		var CCPN = new RegExp("(^\\+([1-9]{1}[0-9]{0,2})){1}\\s*(\\d{4,}$)"); /* +CC PN */
		var PN = new RegExp("(^\\d{4,}$)"); /* PN */
		if (('${phone}' != telefono) && !CCACPN.test(telefono) && !CCPN.test(telefono)) {
			if (PN.test(telefono)) {
				x.value = '${countryPhoneCode}' + " " + telefono;
			} else {
				var mensaje = confirm("<spring:message code="phone.error"/>");
				if (!mensaje) {
					x.value = '${phone}';
				}
			}
		}
	}
	</script>
	
	<jstl:if test="${not empty alert}">
	<script>
	 $(document).ready(function() {
		 alert('<spring:message code="${alert}"/>');
	    });
		
	</script>
</jstl:if>

<form:form action="administrator/save.do" modelAttribute="actorForm">
     
    
    <form:hidden path="id"/>
    <form:hidden path="version"/>     
	    
    <acme:textbox path="userAccountuser" code="administrator.userAccount.username"/>
    <br/>
    <acme:textbox path="userAccountpassword" code="administrator.userAccount.password"/>
    <br/>
    <acme:textbox path="name" code="administrator.name"/>
    <br/>
    <acme:textbox path="middleName" code="administrator.middleName"/>
    <br/>
    <acme:textbox path="surname" code="administrator.surname"/>
    <br/>
    <acme:textbox path="photo" code="administrator.photo"/>
    <br/>
     <acme:textbox path="email" code="administrator.email" placeholder="id@ / alias id@" size="45"/>
    <br/>
    <div>
		<form:label path="phone">
			<spring:message code="administrator.phone" />
		</form:label>
		<form:input path="phone" onblur="phoneFun()" />
		<form:errors path="phone" cssClass="error" />
	</div>
    <br/>
    <acme:textbox path="address" code="sponsor.address"/>
    <br/>
    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>


    <button name="save" type="submit" class="button2">
        <spring:message code="administrator.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancel"
           value="<spring:message code="administrator.cancel" />"
           onclick="relativeRedir('')"/>

</form:form>
