<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Login">
	<div class="authorization">
		<div class="authorization__title title">Login</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=login_post">
			<p class="form__error">${requestScope.error}</p>
			<p class="form__input-label">Login</p><input class="input form__input" name="login" value="${param.login}">
			<p class="form__input-label">Password</p><input class="input form__input" name="password" type="text">
			<button class="button form_button">login</button>
		</form>
	</div>
</tags:general>