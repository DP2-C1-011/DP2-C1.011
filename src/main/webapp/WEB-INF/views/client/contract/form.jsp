<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.contract.form.label.code" path="code"/>	
	<acme:input-moment code="client.contract.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="client.contract.form.label.provider" path="provider"/>
	<acme:input-textbox code="client.contract.form.label.customer" path="customer"/>
	<acme:input-textarea code="client.contract.form.label.goals" path="goals"/>	
	<acme:input-money code="client.contract.form.label.budget" path="budget"/>
	<acme:input-checkbox code="client.contract.form.label.draft-mode" path="draft-mode"/>	
</acme:form>
