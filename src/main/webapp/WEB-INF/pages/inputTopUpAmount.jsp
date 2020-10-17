<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="account_id" type="java.lang.Long" scope="request"/>
<tags:general pageTitle="Top up amount">
	<div class="topup1">
		<div class="title topup1__title">top up</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=top_up_page_get">
			<input type="hidden" name="account_id" value="${account_id}">
			<p class="form__input-label">You want to add</p><input type="text" name="amount" class="input form__input">
			<button class="button form_button">Continue</button>
		</form>
	</div>
</tags:general>