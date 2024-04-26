<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.audit-record.form.label.code" path="code"/>	
	<acme:input-moment code="auditor.audit-record.form.label.period-start" path="periodStart"/>
	<acme:input-moment code="auditor.audit-record.form.label.period-end" path="periodEnd"/>
	<acme:input-select code="auditor.audit-record.form.label.mark" path="mark" choices="${marks}"
				readonly="${acme:anyOf(marks, 'A_PLUS|A|B|C|F|F_MINUS')}"/>
	<acme:input-url code="auditor.audit-record.form.label.optional-link" path="optionalLink"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.audit-record.form.button.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>