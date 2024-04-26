<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.list.label.code" path="code" width="10%"/>
		<acme:list-column code="sponsor.invoice.list.label.quantity" path="quantity" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.tax" path="tax" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.registration-date" path="registrationDate" width="10%"/>
	<acme:list-column code="sponsor.invoice.list.label.due-date" path="dueDate" width="10%"/>
	
</acme:list>

<acme:button test = "${showCreate}" code="sponsor.invoice.list.button.create" action="/sponsor/invoice/create?sponsorshipId=${sponsorshipId}"/>