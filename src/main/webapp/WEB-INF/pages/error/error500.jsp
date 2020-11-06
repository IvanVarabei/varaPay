<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="code_500.">
	<fmt:message key="message" var="message"/>
</fmt:bundle>
<tags:general pageTitle="500">
	<div class="authorization">
		<div class="authorization__title sub-sub-title">${message}</div>
	</div>
</tags:general>