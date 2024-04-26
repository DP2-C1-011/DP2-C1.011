<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.progressLog.form.label.recordId" path="recordId"/>	
	<acme:input-integer code="any.progressLog.form.label.completeness" path="completeness"/>
	<acme:input-textarea code="any.progressLog.form.label.comment" path="comment"/>
	<acme:input-moment code="any.progressLog.form.label.registartionMoment" path="registrationMoment"/>
	<acme:input-textbox code="any.progressLog.form.label.responsible" path="responsible"/>	
</acme:form>
