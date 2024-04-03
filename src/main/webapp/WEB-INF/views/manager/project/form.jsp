<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>	
	<acme:input-textbox code="manager.project.form.label.abstracto" path="abstracto"/>
	<acme:input-textarea code="manager.project.form.label.fatal-error" path="fatal-error"/>
	<acme:input-textbox code="manager.project.form.label.cost" path="cost"/>	
	<acme:input-url code="manager.project.form.label.link" path="link"/>
	<acme:input-checkbox code="manager.project.form.label.draft-mode" path="draft-mode"/>
	
	<acme:button code="manager.project.form.button.userStories" action="/manager/user-story/list?projectId=${id}"/>
	<acme:button code="manager.project.form.button.apply" action="/manager/user-story/create?proiectId=${id}"/>
	
		
</acme:form>
