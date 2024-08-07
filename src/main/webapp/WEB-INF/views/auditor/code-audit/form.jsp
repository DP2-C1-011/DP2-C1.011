<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="auditor.code-audit.form.label.code" path="code"/>	
	<acme:input-select code="auditor.code-audit.form.label.project" path="project" choices="${projects}"/>
	<acme:input-moment code="auditor.code-audit.form.label.execution-date" path="executionDate"/>
	<acme:input-select code="auditor.code-audit.form.label.code-audit-type" path="codeAuditType" choices="${codeAuditTypes}" 
			readonly="${acme:anyOf(codeAuditTypes, 'STATIC|DYNAMIC')}"/>
	<acme:input-textbox code="auditor.code-audit.form.label.corrective-actions" path="correctiveActions"/>	
	<acme:input-url code="auditor.code-audit.form.label.optional-link" path="optionalLink"/>
	<acme:input-checkbox code="auditor.code-audit.form.label.draft-mode" path="draftMode"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode}">
			<acme:submit code="auditor.code-audit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.code-audit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.code-audit.form.button.publish" action="/auditor/code-audit/publish"/>
			<acme:button code="auditor.code-audit.form.button.audit-records" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && !draftMode}">
			<acme:button code="auditor.code-audit.form.button.audit-records" action="/auditor/audit-record/list?codeAuditId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.code-audit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>
	</jstl:choose>		
	
</acme:form>