<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.training-module.form.label.code" path="code"/>
	<acme:input-textbox code="any.training-module.form.label.project" path="project" readonly="true"/>
	<acme:input-moment code="any.training-module.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="any.training-module.form.label.details" path="details"/>
	<acme:input-textbox code="any.training-module.form.label.difficulty-level" path="difficultyLevel"/>
	<acme:input-moment code="any.training-module.form.label.update-moment" path="updateMoment"/>	
	<acme:input-url code="any.training-module.form.label.optional-link" path="optionalLink"/>
	<acme:input-integer code="any.training-module.form.label.total-time" path="totalTime"/>
</acme:form>
