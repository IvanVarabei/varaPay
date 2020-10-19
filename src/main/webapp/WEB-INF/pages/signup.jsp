<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Create account">
	<c:if test="${not empty requestScope.errors}">
		<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	</c:if>
	<div class="authorization">
		<div class="authorization__title title">Create account</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=signup_post">
			<p class="form__input-label">Login</p><input class="input form__input" name="login" value="${param.login}">
			<p class="form__error">${errors.login}</p>
			<p class="form__input-label">Password</p><input class="input form__input" name="password" type="password">
			<p class="form__error">${errors.password}</p>
			<p class="form__input-label">Repeat password</p><input class="input form__input" name="repeatPassword" type="password">
			<p class="form__error">${errors.repeatPassword}</p>
			<p class="form__input-label">First name</p><input class="input form__input" name="firstName" value="${param.firstName}">
			<p class="form__error">${errors.firstName}</p>
			<p class="form__input-label">Last name</p><input class="input form__input" name="lastName" value="${param.lastName}">
			<p class="form__error">${errors.lastName}</p>
			<p class="form__input-label">Email</p><input class="input form__input" name="email" value="${param.email}">
			<p class="form__error">${errors.email}</p>
			<p class="form__input-label">Date birth</p><input class="input form__input" name="birth" value="${not empty param.birth ? param.birth : '2000-01-01'}" type="date" min="1920-01-01" max="2010-01-01">
			<p class="form__error">${errors.birth}</p>
			<button class="button form_button">sign up</button>
		</form>
	</div>
</tags:general>