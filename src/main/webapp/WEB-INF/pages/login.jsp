<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Login">
	<fmt:setLocale value="${sessionScope.locale}"/>
	<fmt:bundle basename="content" prefix="login.">
		<fmt:message key="password" var="password"/>
		<fmt:message key="log_in" var="log_in"/>
		<fmt:message key="fail_message" var="fail_message"/>
	</fmt:bundle>

	<div class="authorization">
		<div class="authorization__title title">${log_in}</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=login_post">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${requestScope.error}</p></c:if>
			<p class="form__input-label">Login</p><input class="input form__input" name="login" value="${param.login}" minlength="3" maxlength="20" required>
			<p class="form__input-label">Password</p><input class="input form__input" name="password" type="text" minlength="3" maxlength="20" required>
			<button class="button form_button">login</button>
		</form>
		<a href="${pageContext.servletContext.contextPath}/mainServlet?command=RECOVER_PASSWORD_GET">Forgot your password?</a>
	</div>
</tags:general>