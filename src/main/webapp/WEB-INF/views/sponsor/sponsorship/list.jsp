<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.details" path="details" width="10%"/>
</acme:list>

<acme:button test = "${showCreate}" code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>