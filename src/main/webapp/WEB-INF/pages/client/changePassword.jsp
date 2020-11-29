<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="change_password.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="old_password" var="old_password"/>
	<fmt:message key="password" var="password"/>
	<fmt:message key="repeat_password" var="repeat_password"/>
	<fmt:message key="button" var="button"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="length" var="client_password_error"/>
	<fmt:message key="different_passwords" var="client_repeat_password_error"/>
</fmt:bundle>
<c:if test="${not empty requestScope.errors}">
	<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	<fmt:bundle basename="content" prefix="error.">
		<c:if test="${errors.oldPassword != param.oldPassword}">
			<fmt:message key="${errors.oldPassword}" var="old_password_error"/>
		</c:if>
		<c:if test="${errors.password != param.password}">
			<fmt:message key="${errors.password}" var="password_error"/>
		</c:if>
		<c:if test="${errors.repeatPassword != param.repeatPassword}">
			<fmt:message key="${errors.repeatPassword}" var="repeat_password_error"/>
		</c:if>
	</fmt:bundle>
</c:if>
<tags:general pageTitle="${title}">
	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=change_password_post">

			<p class="form__input-label">${old_password}</p>
			<input class="input form__input" name="oldPassword" type="password" pattern=".{3,20}" required minlength="3"
						 maxlength="20"
						 oninvalid="this.setCustomValidity('${client_password_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${old_password_error}</p>

			<p class="form__input-label">${password}</p>
			<input class="input form__input" id="password" name="password" type="password" pattern=".{3,20}" required
						 minlength="3" maxlength="20"
						 oninvalid="this.setCustomValidity('${client_password_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${password_error}</p>

			<p class="form__input-label">${repeat_password}</p>
			<input class="input form__input" id="password-check" name="repeatPassword" type="password" minlength="3"
						 maxlength="20" title="">
			<p class="form__error">${repeat_password_error}</p>

			<button class="button form_button">${button}</button>
		</form>
	</div>
	<script src="js/passwordConformity.js"></script>
	<script>checkPasswordsConformity('#password', '#password-check', '${client_repeat_password_error}')</script>
</tags:general>