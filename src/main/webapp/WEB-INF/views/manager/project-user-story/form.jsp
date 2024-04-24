<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-select code="manager.link.form.label.userStory" path="project" choices="${projects}"/>		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'delete')}">
			<acme:submit code="manager.link.form.button.delete" action="/manager/project-user-story/delete?userStoryId=${userStoryId}"/>
		</jstl:when>
		<jstl:when test="${acme:matches(_command, 'create')}">
			<acme:submit code="manager.link.form.button.create" action="/manager/project-user-story/create?userStoryId=${userStoryId}"/>			
		</jstl:when>
	</jstl:choose>	
</acme:form>