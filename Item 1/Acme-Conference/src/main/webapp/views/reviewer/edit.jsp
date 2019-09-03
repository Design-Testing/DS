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

<form:form action="reviewer/save.do" modelAttribute="reviewerForm">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="userAccountpassword"/>
    <form:hidden path="userAccountuser"/>

    
    <acme:textbox path="name" code="reviewer.name"/>
    <br/>
    <acme:textbox path="surname" code="reviewer.surname"/>
    <br/>
    <acme:textbox path="middleName" code="reviewer.middleName"/>
    <br/>
    <acme:textbox path="photo" code="reviewer.photo"/>
    <br/>
    <acme:textbox path="email" code="reviewer.email" placeholder="id@domain / alias id@domain " size="45"/>
    <br/>
     <div>
		<form:label path="phone">
			<spring:message code="reviewer.phone" />
		</form:label>
		<form:input path="phone" onblur="phoneFun()" />
		<form:errors path="phone" cssClass="error" />
	</div>
    <br/>
    <acme:textbox path="address" code="reviewer.address"/>
    <br/>
    <acme:textarea path="keywords" code="reviewer.keywords"/>
    <br/>

    
    <jstl:if test="${not empty msgerror  }">
    	<h5 style="color: red;"><spring:message code="${msgerror}"/></h5>
    </jstl:if>
  
	
    <br/>
    <br/>


    <button name="save" type="submit" class="button2">
        <spring:message code="reviewer.save"/>
    </button>

    <input type="button" class="btn btn-danger" name="cancell"
           value="<spring:message code="reviewer.cancel" />"
           onclick="relativeRedir('reviewer/display.do');"/>

</form:form>
