<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="verify_email.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="temp_code" var="temp_code"/>
	<fmt:message key="submit" var="submit"/>
	<fmt:message key="fail_message" var="fail_message"/>
</fmt:bundle>
<tags:general pageTitle="${title}">
	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=verify_email_post">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${fail_message}</p></c:if>
			<p class="form__input-label">${temp_code}</p><input class="input form__input" name="tempCode">
			<button class="button form_button">${submit}</button>
		</form>
	</div>
</tags:general>