<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Create profile">
	<c:if test="${not empty requestScope.errors}">
		<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	</c:if>
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
		<fmt:message key="button" var="button"/>

		<fmt:message key="error.login_error" var="login_error"/>
		<fmt:message key="error.already_exists" var="already_exists"/>
	</fmt:bundle>


	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet">
			<input type="hidden" name="command" value="signup_post">
			<p class="form__input-label">${log_in}</p><input class="input form__input" name="login" value="${param.login}">
			<c:if test="${not empty errors.login}">
				<p class="form__error">${
				errors.login eq 'login_error' ? login_error :
				errors.login eq 'already_exists' ? already_exists : ''}</p>
			</c:if>
			<p class="form__input-label">${password}</p><input class="input form__input" name="password" type="password">
			<p class="form__error">${errors.password}</p>
			<p class="form__input-label">${repeat_password}</p><input class="input form__input" name="repeatPassword"
																																type="password">
			<p class="form__error">${errors.repeatPassword}</p>
			<p class="form__input-label">${firstname}</p><input class="input form__input" name="firstName"
																													value="${param.firstName}">
			<p class="form__error">${errors.firstName}</p>
			<p class="form__input-label">${lastname}</p><input class="input form__input" name="lastName"
																												 value="${param.lastName}">
			<p class="form__error">${errors.lastName}</p>
			<p class="form__input-label">${email}</p><input class="input form__input" name="email" value="${param.email}">
			<p class="form__error">${errors.email}</p>
			<p class="form__input-label">${birth}</p><input class="input form__input" name="birth"
																											value="${not empty param.birth ? param.birth : '2000-01-01'}"
																											type="date" min="1920-01-01" max="2010-01-01">
			<p class="form__error">${errors.birth}</p>
			<p class="form__input-label">Secret</p><input class="input form__input" name="secret_word" value="${param.secret_word}">
			<p class="form__error">${errors.email}</p>
			<button class="button form_button">${button}</button>
		</form>
	</div>
</tags:general>