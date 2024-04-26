<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="sponsor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.num-tax-under-21"/>
		</th>
		<td>
			<acme:print value="${taxUnder21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.num-link-sponsorships"/>
		</th>
		<td>
			<acme:print value="${linkedSponsorships}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${averageSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${minimumSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${maximumSponsorship}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${averageInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${deviationInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${minimumInvoice}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${maximumInvoice}"/>
		</td>
	</tr>
</table>
<acme:return/>