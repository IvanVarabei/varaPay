<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="card.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="blocked" var="blocked"/>
	<fmt:message key="active" var="active"/>
	<fmt:message key="from" var="from"/>
	<fmt:message key="to" var="to"/>
	<fmt:message key="amount" var="amount"/>
	<fmt:message key="cvc" var="cvc"/>
	<fmt:message key="pay" var="pay"/>
	<fmt:message key="history" var="history"/>
	<fmt:message key="instant" var="instant"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="card_number" var="client_card_number_error"/>
	<fmt:message key="can_not_be_empty" var="can_not_be_empty"/>
	<fmt:message key="amount" var="client_amount_error"/>
	<fmt:message key="cvc" var="client_cvc_error"/>
	<fmt:message key="less_than" var="client_less_than_error"/>
</fmt:bundle>
<c:if test="${not empty requestScope.errors}">
	<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	<fmt:bundle basename="content" prefix="error.">
		<c:if test="${errors.amount != param.amount}">
			<fmt:message key="${errors.amount}" var="amount_error"/>
		</c:if>
		<c:if test="${errors.cvc != param.cvc}">
			<fmt:message key="${errors.cvc}" var="cvc_error"/>
		</c:if>
		<c:if test="${errors.cardNumber != param.cardNumber}">
			<fmt:message key="${errors.cardNumber}" var="destination_card_number_error"/>
		</c:if>
		<c:if test="${errors.validThru != param.validThru}">
			<fmt:message key="${errors.validThru}" var="valid_thru_error"/>
		</c:if>
		<c:if test="${errors.cardId != param.cardId}">
			<fmt:message key="${errors.cardId}" var="card_id_error"/>
		</c:if>
	</fmt:bundle>
</c:if>
<jsp:useBean id="card" type="com.epam.varapay.model.entity.Card" scope="request"/>
<tags:general pageTitle="${title}">
	<div class="card">
		<div class="account__info account__title">
			<div class="title ">${title}</div>
			<div class="sub-sub-title ${card.account.active eq true ? 'green' : 'red'}">
					${card.account.active eq true ? active : blocked}
			</div>
		</div>
		<div class="card__payment">
			<div class="card__sub-title1 sub-title">${from}
				<p class="card__sub-title-error">${card_id_error}</p>
			</div>
			<div class="card__img1">
				<div class="sub-title card__balance">${card.account.balance}$</div>
				<div>
					<input class="input card__input-number" value="${f:formatCardNumber(card.cardNumber)}" readonly>
				</div>
				<div>
					<input class="input card__input-date" value="${f:formatLocalDate(card.validThru, "MM/yy")}" readonly>
				</div>
				<img src="img/card1.png"/>
			</div>
			<div class="card__sub-title2 sub-title">${to}
				<p class="card__sub-title-error">${not empty destination_card_number_error ?
								destination_card_number_error : valid_thru_error}</p>
			</div>
			<div class="card__img2">
				<div>
					<input class="input card__input-number" name="cardNumber" form="makePayment" value="${param.cardNumber}"
								 pattern="^(\s?\d\s?){16}$" required ${card.account.active ? '' : 'disabled'}
								 onchange="this.setCustomValidity('')"
								 oninvalid="this.setCustomValidity('${client_card_number_error}')">
				</div>
				<div>
					<input class="input card__input-date" type="month" name="validThru" form="makePayment" required min="2023-10"
								 value="${param.validThru}" ${card.account.active ? '' : 'disabled'}
								 onchange="this.setCustomValidity('')" oninvalid="this.setCustomValidity('${can_not_be_empty}')">
				</div>
				<img src="img/card1.png"/>
			</div>
		</div>
		<form id="makePayment" class="form card__form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=make_payment_post&page=
					${requestScope.currentPage}">

			<p class="form__input-label">${amount}</p>
			<input class="input form__input" name="amount" form="makePayment" type="number" value="${param.amount}"
				${card.account.active ? '' : 'disabled'}
						 step="0.01" max="${card.account.balance}" required onchange="this.setCustomValidity('')"
						 oninvalid="this.setCustomValidity('${client_amount_error}, ${client_less_than_error} ${card.account.balance}')">
			<p class="form__error">${amount_error}</p>

			<p class="form__input-label">${cvc}</p>
			<input class="input form__input" name="cvc" value="${param.cvc}" ${card.account.active ? '' : 'disabled'}
						 pattern="^\d{3}$" required
						 onchange="this.setCustomValidity('')" oninvalid="this.setCustomValidity('${client_cvc_error}')">
			<p class="form__error">${cvc_error}</p>

			<input type="hidden" name="cardId" value="${card.id}" form="makePayment">
			<button class="button form_button" ${card.account.active ? '' : 'disabled'}>${pay}</button>
		</form>
		<c:if test="${not empty requestScope.payments}">
			<jsp:useBean id="payments" type="java.util.List" scope="request"/>
		<div class="sub-title card__sub-title">${history}</div>
		<div class="payment-history">
			<div class="payment-history__item">${amount}</div>
			<div class="payment-history__item">${from}</div>
			<div class="payment-history__item">${to}</div>
			<div class="payment-history__item">${instant}</div>
			<c:forEach var="payment" items="${payments}">
				<div class="payment-history__item">${payment.amount}$</div>
				<div class="payment-history__item">
						${payment.sourceCard.cardNumber}<br><tags:localDate date="${payment.sourceCard.validThru}"
																																pattern="MM/yy"/></div>
				<div class="payment-history__item">${payment.destinationCard.cardNumber}<br><tags:localDate
								date="${payment.destinationCard.validThru}" pattern="MM/yy"/></div>
				<div class="payment-history__item">
					<javatime:format value="${payment.paymentInstant}" style="MS"/>
				</div>
			</c:forEach>
		</div>
		<div class="pagination">
			<tags:pagination amountOfPages="${requestScope.amountOfPages}" currentPage="${requestScope.currentPage}" url="
			${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&cardId=${card.id}"/>
			</c:if>
		</div>
</tags:general>