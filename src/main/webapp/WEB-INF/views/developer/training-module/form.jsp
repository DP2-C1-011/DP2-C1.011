<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-module.form.label.code" path="code"/>	
	<acme:input-textbox code="developer.training-module.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="developer.training-module.form.label.details" path="details"/>
	<acme:input-textbox code="developer.training-module.form.label.difficulty-level" path="difficultyLevel"/>
	<acme:input-textbox code="developer.training-module.form.label.update-moment" path="updateMoment"/>	
	<acme:input-url code="developer.training-module.form.label.optional-link" path="optionalLink"/>
	<acme:input-textbox code="developer.training-module.form.label.total-time" path="totalTime"/>
	<acme:input-checkbox code="developer.training-module.form.label.draft-mode" path="draftMode"/>	
</acme:form>
