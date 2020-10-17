<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="account_id" type="java.lang.Long" scope="request"/>
<jsp:useBean id="amount" type="java.math.BigDecimal" scope="request"/>
<tags:general pageTitle="Top up">
	<div class="topup2">
		<div class="title topup2__title">top up</div>
		<div class="steps">
			<div class="step">1. Send 2343 BTC to this wallet jlksadjfl34rj43ljr34t234t234f231345343245 and set comment your login</div>
			<div class="step">2. Put the reference about BTC transaction into "Message"</div>
			<div class="step">3. Push submit button</div>
		</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=PLACE_TOP_UP_BID_POST">
			<p class="form__input-label">Message</p><input type="text" name="client_message" class="input form__input">
			<input type="hidden" name="account_id" value="${account_id}">
			<input type="hidden" name="amount" value="${amount}">
			<button class="button form_button">Submit</button>
		</form>
	</div>
</tags:general>