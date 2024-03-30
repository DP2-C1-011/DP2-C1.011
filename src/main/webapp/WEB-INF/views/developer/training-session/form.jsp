<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-session.form.label.code" path="code"/>	
	<acme:input-textbox code="developer.training-session.form.label.start-moment" path="startMoment"/>
	<acme:input-textbox code="developer.training-session.form.label.finish-moment" path="finishMoment"/>
	<acme:input-textbox code="developer.training-session.form.label.location" path="location"/>
	<acme:input-textbox code="developer.training-session.form.label.instructor" path="instructor"/>	
	<acme:input-textbox code="developer.training-session.form.label.contact-email" path="contactEmail"/>
	<acme:input-url code="developer.training-session.form.label.optional-link" path="optionalLink"/>
</acme:form>
