<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Verify email">
	<div class="authorization">
		<div class="authorization__title title">Verify create card</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=verify_create_card_post">
			<p class="form__error">${requestScope.error}</p>
			<p class="form__input-label">Temp code from email</p><input class="input form__input" name="tempCode">
			<button class="button form_button">Submit</button>
		</form>
	</div>
</tags:general>