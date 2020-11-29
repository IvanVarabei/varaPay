<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="signup.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="log_in" var="log_in"/>
	<fmt:message key="password" var="password"/>
	<fmt:message key="repeat_password" var="repeat_password"/>
	<fmt:message key="firstname" var="firstname"/>
	<fmt:message key="lastname" var="lastname"/>
	<fmt:message key="email" var="email"/>
	<fmt:message key="birth" var="birth"/>
	<fmt:message key="secret_word" var="secret_word"/>
	<fmt:message key="button" var="button"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="login" var="client_login_error"/>
	<fmt:message key="length" var="client_password_error"/>
	<fmt:message key="different_passwords" var="client_repeat_password_error"/>
	<fmt:message key="name" var="client_name_error"/>
	<fmt:message key="email" var="client_email_error"/>
</fmt:bundle>
<c:if test="${not empty requestScope.errors}">
	<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	<fmt:bundle basename="content" prefix="error.">
		<c:if test="${errors.login != param.login}">
			<fmt:message key="${errors.login}" var="login_error"/>
		</c:if>
		<c:if test="${errors.password != param.password}">
			<fmt:message key="${errors.password}" var="password_error"/>
		</c:if>
		<c:if test="${errors.repeatPassword != param.repeatPassword}">
			<fmt:message key="${errors.repeatPassword}" var="repeat_password_error"/>
		</c:if>
		<c:if test="${errors.firstName != param.firstName}">
			<fmt:message key="${errors.firstName}" var="firstname_error"/>
		</c:if>
		<c:if test="${errors.lastName != param.lastName}">
			<fmt:message key="${errors.lastName}" var="lastname_error"/>
		</c:if>
		<c:if test="${errors.email != param.email}">
			<fmt:message key="${errors.email}" var="email_error"/>
		</c:if>
		<c:if test="${errors.secretWord != param.secretWord}">
			<fmt:message key="${errors.secretWord}" var="secret_word_error"/>
		</c:if>
	</fmt:bundle>
</c:if>
<tags:general pageTitle="Create profile">
	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet">
			<input type="hidden" name="command" value="signup_post">

			<p class="form__input-label">${log_in}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="login" value="${param.login}" pattern="^[a-zA-Z0-9_]{3,20}$" required
						 minlength="3" maxlength="20"
						 oninvalid="this.setCustomValidity('${client_login_error}')" onchange="this.setCustomValidity('')"/>
			<p class="form__error">${login_error}</p>

			<p class="form__input-label">${password}<span class="form__asterisk">*</span></p>
			<input class="input form__input" id="password" name="password" type="password" pattern=".{3,20}" minlength="3"
						 maxlength="20" required oninvalid="this.setCustomValidity('${client_password_error}')"
						 onchange="this.setCustomValidity('')">
			<p class="form__error">${password_error}</p>

			<p class="form__input-label">${repeat_password}<span class="form__asterisk">*</span></p>
			<input class="input form__input" id="password-check" name="repeatPassword" type="password" minlength="3"
						 maxlength="20" title="">
			<p class="form__error">${repeat_password_error}</p>

			<p class="form__input-label">${firstname}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="firstName" value="${param.firstName}" pattern="^[A-Za-zА-Яа-яЁё]{3,20}$"
						 minlength="3" maxlength="20"
						 required oninvalid="this.setCustomValidity('${client_name_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${firstname_error}</p>

			<p class="form__input-label">${lastname}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="lastName" value="${param.lastName}" pattern="^[A-Za-zА-Яа-яЁё]{3,20}$"
						 minlength="3" maxlength="20"
						 required oninvalid="this.setCustomValidity('${client_name_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${lastname_error}</p>

			<p class="form__input-label">${email}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="email" value="${param.email}"
						 pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
						 required oninvalid="this.setCustomValidity('${client_email_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${email_error}</p>

			<p class="form__input-label">${birth}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="birth" value="${not empty param.birth ? param.birth : '2000-01-01'}"
						 type="date" min="1920-01-01" max="2010-01-01">

			<p class="form__input-label">${secret_word}<span class="form__asterisk">*</span></p>
			<input class="input form__input" name="secretWord" pattern="^[A-Za-zА-Яа-яЁё]{3,20}$" type="password"
						 minlength="3" maxlength="20"
						 required oninvalid="this.setCustomValidity('${client_name_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${secret_word_error}</p>
			<button class="button form_button">${button}</button>
		</form>
	</div>
	<script src="js/passwordConformity.js"></script>
	<script>checkPasswordsConformity('#password', '#password-check', '${client_repeat_password_error}')</script>
</tags:general>