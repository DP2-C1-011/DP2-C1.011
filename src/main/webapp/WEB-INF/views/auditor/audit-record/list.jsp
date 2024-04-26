<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list navigable="true">
	<acme:list-column code="auditor.audit-record.list.label.code" path="code" width="10%"/>
	<acme:list-column code="auditor.audit-record.list.label.periodStart" path="periodStart" width="10%"/>
	<acme:list-column code="auditor.audit-record.list.label.periodEnd" path="periodEnd" width="10%"/>
	<acme:list-column code="auditor.audit-record.list.label.mark" path="mark" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="auditor.audit-record.list.button.create" action="/auditor/audit-record/create?codeAuditId=${codeAuditId}"/>
</jstl:if>