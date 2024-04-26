<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.contract.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.contract.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
</acme:list>
