<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment" readonly="true"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.start-date" path="startDate" readonly="true"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.end-date" path="endDate" readonly="true"/>	
	<acme:input-money code="sponsor.sponsorship.form.label.amount" path="amount" readonly="true"/>
	<acme:input-checkbox code="sponsor.sponsorship.form.label.financial" path="financial" readonly="true"/>
	<acme:input-url code="sponsor.sponsorship.form.label.email" path="email" readonly="true"/>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link" readonly="true"/>

</acme:form>