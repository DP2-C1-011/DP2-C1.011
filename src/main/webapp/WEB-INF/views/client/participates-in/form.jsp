<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:input-select code="client.participatesIn.form.label.project" path="project" choices="${projects}" readonly="true"/>
			<acme:submit code="client.participatesIn.form.button.delete" action="/client/participates-in/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="client.participatesIn.form.label.project" path="project" choices="${projects}"/>
			<acme:submit code="client.participatesIn.form.button.create" action="/client/participates-in/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
