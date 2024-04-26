<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-record.list.label.code" path="code"  width="80%"/>
	<acme:list-column code="auditor.audit-record.list.label.mark" path="mark"  width="20%"/>
</acme:list>
<acme:button test="${showCreate}" code="auditor.audit-record.list.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
