<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Verify email">
	<div class="authorization">
		<div class="authorization__title title">Verify email</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=verify_email_post">
			<p class="form__error">${requestScope.error}</p>
			<p class="form__input-label">Temp password</p><input class="input form__input" name="tempPassword">
			<button class="button form_button">Submit</button>
		</form>
	</div>
</tags:general>