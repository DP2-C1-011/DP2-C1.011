<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code ="auditor.code-audit.list.label.code" path="code" width="20%"/>
	<acme:list-column code ="auditor.code-audit.list.label.corrective-actions" path="correctiveActions" width="20%"/>
</acme:list>

<acme:button test = "${showCreate}" code="auditor.code-audit.list.button.create" action="/auditor/code-audit/create"/>