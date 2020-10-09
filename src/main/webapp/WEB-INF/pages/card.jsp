<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="card" type="com.varabei.ivan.model.entity.Card" scope="request"/>
<tags:general pageTitle="Card">
	<div class="card">
		<div class="title">Card</div>
		<div class="card__payment">
			<div class="card__sub-title1 sub-title">from</div>
			<div class="card__img1">
				<div class="sub-title card__balance">${card.accountInfo.balance}$</div>
				<div>
					<input class="input card__input-number" value="${card.cardNumber}" readonly>
				</div>
				<div>
					<input class="input card__input-date" value="${card.validThruDate}" readonly>
				</div>
				<img src="img/card1.png"/>
			</div>
			<div class="card__sub-title2 sub-title">to</div>
			<div class="card__img2">
				<div>
					<input class="input card__input-number" name="destinationCardNumber" form="makePayment">
				</div>
				<div>
					<input class="input card__input-date" type="month" name="destinationCardDate" form="makePayment">
				</div>
				<img src="img/card1.png"/>
			</div>
		</div>
		<form id="makePayment" class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=make_payment_post">
			<p class="form__input-label">Amount</p><input class="input form__input" name="amount" form="makePayment">
			<p class="form__input-label">Your CVC</p><input class="input form__input" name="cvc">
			<input type="hidden" name="cardId" value="${card.id}" form="makePayment">
			<button class="button form_button">pay</button>
		</form>
	</div>
</tags:general>