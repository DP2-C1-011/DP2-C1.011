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
		<td/>
		<td>
			<acme:print value="${taxUnder21}"/>
		</td>
		<td/>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.num-link-sponsorships"/>
		</th>
		<td/>
		<td>
			<acme:print value="${linkedSponsorships}"/>
		</td>
		<td/>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${averageSponsorship[0]}"/>
		</td>
		<td>
			<acme:print value="${averageSponsorship[1]}"/>
		</td>
		<td>
			<acme:print value="${averageSponsorship[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorship[0]}"/>
		</td>
		<td>
			<acme:print value="${deviationSponsorship[1]}"/>
		</td>
		<td>
			<acme:print value="${deviationSponsorship[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${minimumSponsorship[0]}"/>
		</td>
		<td>
			<acme:print value="${minimumSponsorship[1]}"/>
		</td>
		<td>
			<acme:print value="${minimumSponsorship[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${maximumSponsorship[0]}"/>
		</td>
		<td>
			<acme:print value="${maximumSponsorship[1]}"/>
		</td>
		<td>
			<acme:print value="${maximumSponsorship[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${averageInvoice[0]}"/>
		</td>
		<td>
			<acme:print value="${averageInvoice[1]}"/>
		</td>
		<td>
			<acme:print value="${averageInvoice[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.dev-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${deviationInvoice[0]}"/>
		</td>
		<td>
			<acme:print value="${deviationInvoice[1]}"/>
		</td>
		<td>
			<acme:print value="${deviationInvoice[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${minimumInvoice[0]}"/>
		</td>
		<td>
			<acme:print value="${minimumInvoice[1]}"/>
		</td>
		<td>
			<acme:print value="${minimumInvoice[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${maximumInvoice[0]}"/>
		</td>
		<td>
			<acme:print value="${maximumInvoice[1]}"/>
		</td>
		<td>
			<acme:print value="${maximumInvoice[2]}"/>
		</td>
	</tr>
</table>
<acme:return/>