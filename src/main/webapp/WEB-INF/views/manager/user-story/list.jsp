<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user_story.list.label.title" path="title" width="20%"/>
	<acme:list-column code="manager.user_story.list.label.description" path="description" width="20%"/>
</acme:list>