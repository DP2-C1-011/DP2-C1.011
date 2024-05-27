<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.contract.form.label.code" path="code" readonly="true"/>	
	<acme:input-moment code="any.contract.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.provider" path="provider" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.customer" path="customer" readonly="true"/>
	<acme:input-textarea code="any.contract.form.label.goals" path="goals" readonly="true"/>	
	<acme:input-money code="any.contract.form.label.budget" path="budget" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.project" path="project" readonly="true"/>
	<acme:input-money code="client.contract.form.label.systemCurrencyBudget" path="systemCurrencyBudget" readonly="true"/>
	
	<acme:button code="any.contract.form.button.progressLogs" action="/any/progress-log/list?masterId=${id}"/>
</acme:form>
