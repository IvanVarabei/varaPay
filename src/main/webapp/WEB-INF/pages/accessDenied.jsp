<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="access_denied.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="message" var="message"/>
</fmt:bundle>
<tags:general pageTitle="${title}">
	<div class="authorization">
		<div class="title authorization__title">Access denied</div>
		<div class="sub-sub-title">You do not have enough rights</div>
	</div>
</tags:general>