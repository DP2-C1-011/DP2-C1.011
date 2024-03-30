<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.user_story.form.label.title" path="title"/>	
	<acme:input-textbox code="manager.user_story.form.label.description" path="description"/>
	<acme:input-textbox code="manager.user_story.form.label.estimatedCost" path="estimatedCost"/>
	<acme:input-textarea code="manager.user_story.form.label.acceptanceCriteria" path="acceptanceCriteria"/>
	<acme:input-url code="manager.user_story.form.label.link" path="link"/>
</acme:form>