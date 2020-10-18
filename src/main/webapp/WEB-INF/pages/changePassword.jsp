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
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=change_password_post">
			<p class="form__input-label">Old password</p><input class="input form__input" name="oldPassword" type="password">
			<p class="form__error">${errors.oldPassword}</p>
			<p class="form__input-label">New password</p><input class="input form__input" name="password" type="password">
			<p class="form__error">${errors.password}</p>
			<p class="form__input-label">Repeat new password</p><input class="input form__input" name="repeatPassword" type="password">
			<p class="form__error">${errors.repeatPassword}</p>
			<button class="button form_button">Change password</button>
		</form>
	</div>
</tags:general>