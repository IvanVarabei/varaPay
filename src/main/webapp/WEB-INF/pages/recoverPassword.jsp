<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="recover_password.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="email" var="email"/>
	<fmt:message key="button" var="button"/>
</fmt:bundle>
<tags:general pageTitle="${title}">
	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=recover_password_post">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${requestScope.error}</p></c:if>
			<p class="form__input-label">${email}</p><input class="input form__input" name="email" value="${param.login}">
			<button class="button form_button">${button}</button>
		</form>
	</div>
</tags:general>