<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Login">
	<div class="authorization">
		<div class="authorization__title title">Login page</div>
		<form action="" class="form">
			<p class="form__input-lable">Enter login</p><input class="input form__input">
			<p class="form__input-lable">Enter password</p><input class="input form__input">
			<button class="button form_button">login</button>
		</form>
	</div>
</tags:general>