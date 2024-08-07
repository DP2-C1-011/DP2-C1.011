<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.sponsor.form.label.name" path="name"/>
	<acme:input-textbox code="authenticated.sponsor.form.label.expectedBenefits" path="expectedBenefits"/>
	<acme:input-email code="authenticated.sponsor.form.label.optionalEmail" path="optionalEmail"/>
	<acme:input-url code="authenticated.sponsor.form.label.optionalLink" path="optionalLink"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.sponsor.form.button.create" action="/authenticated/sponsor/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.sponsor.form.button.update" action="/authenticated/sponsor/update"/>
</acme:form>
