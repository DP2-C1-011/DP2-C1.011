<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.participatesIn.list.label.project" path="project" width="20%"/>
</acme:list>

<acme:button test="${showCreate}" code="client.participatesIn.list.button.create" action="/client/participates-in/create"/>