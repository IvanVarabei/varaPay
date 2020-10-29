<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="login.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="password" var="password"/>
	<fmt:message key="log_in" var="log_in"/>
	<fmt:message key="forgot_password" var="forgot_password"/>
	<fmt:message key="fail_message" var="fail_message"/>
</fmt:bundle>
<tags:general pageTitle="Login">
		<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=login_post">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${fail_message}</p></c:if>
			<p class="form__input-label">${log_in}</p><input class="input form__input" name="login" value="${param.login}" minlength="3" maxlength="20">
			<p class="form__input-label">${password}</p><input class="input form__input" name="password" type="text" minlength="3" maxlength="20">
			<button class="button form_button">${title}</button>
		</form>
		<a href="${pageContext.servletContext.contextPath}/mainServlet?command=RECOVER_PASSWORD_GET">${forgot_password}</a>
	</div>
</tags:general>