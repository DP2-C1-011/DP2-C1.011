<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="10%"/>
</acme:list>

<acme:button test = "${showCreate}" code="administrator.banner.list.button.create" action="/administrator/banner/create"/>