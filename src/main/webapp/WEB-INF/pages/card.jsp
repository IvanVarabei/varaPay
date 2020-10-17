<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<jsp:useBean id="card" type="com.varabei.ivan.model.entity.Card" scope="request"/>
<tags:general pageTitle="Card">
	<div class="card">
		<div class="title">Card</div>
		<div class="card__payment">
			<div class="card__sub-title1 sub-title">from</div>
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
		<form id="makePayment" class="form card__form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=make_payment_post">
			<p class="form__input-label">Amount</p><input class="input form__input" name="amount" form="makePayment">
			<p class="form__input-label">Your CVC</p><input class="input form__input" name="cvc">
			<input type="hidden" name="card_id" value="${card.id}" form="makePayment">
			<button class="button form_button">pay</button>
		</form>
		<c:if test="${not empty requestScope.payments}">
			<jsp:useBean id="payments" type="java.util.List" scope="request"/>
			<div class="sub-title card__sub-title">payment history</div>
			<div class="payment-history">
				<div class="payment-history__item">Amount</div>
				<div class="payment-history__item">From</div>
				<div class="payment-history__item">To</div>
				<div class="payment-history__item">Instant</div>
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
			<table border="1" cellpadding="5" cellspacing="5">
				<tr>
					<c:forEach begin="${1}" end="${noOfPages}" var="i">
						<c:choose>
							<c:when test="${i < currentPage - 2 and i eq 1}">
								<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${i}&card_id=${card.id}">${i}...</a></td>
							</c:when>
							<c:when test="${i eq noOfPages and currentPage < i -2}">
								<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${i}&card_id=${card.id}">...${i}</a></td>
							</c:when>
							<c:when test="${i eq currentPage}">
								<td>${i}</td>
							</c:when>
							<c:when test="${i < currentPage - 2}">
							</c:when>
							<c:when test="${i > currentPage + 2}">
							</c:when>

							<c:otherwise>
								<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${i}&card_id=${card.id}">${i}</a></td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</table>

<%--			<table border="1" cellpadding="5" cellspacing="5">--%>
<%--				<tr>--%>
<%--					<c:forEach begin="${currentPage < 4 ? 1 : currentPage-2}" end="${currentPage < noOfPages -2 ? currentPage+2 : noOfPages}" var="i">--%>
<%--						<c:choose>--%>
<%--							<c:when test="${currentPage eq i}">--%>
<%--								<td>${i}</td>--%>
<%--							</c:when>--%>
<%--							<c:otherwise>--%>
<%--								<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${i}&card_id=${card.id}">${i}</a></td>--%>
<%--							</c:otherwise>--%>
<%--						</c:choose>--%>
<%--					</c:forEach>--%>
<%--				</tr>--%>
<%--			</table>--%>


<%--			<table border="1" cellpadding="5" cellspacing="5">--%>
<%--				<tr>--%>
<%--					<c:forEach begin="1" end="${noOfPages}" var="i">--%>
<%--						<c:choose>--%>
<%--							<c:when test="${currentPage eq i}">--%>
<%--								<td>${i}</td>--%>
<%--							</c:when>--%>
<%--							<c:otherwise>--%>
<%--								<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${i}&card_id=${card.id}">${i}</a></td>--%>
<%--							</c:otherwise>--%>
<%--						</c:choose>--%>
<%--					</c:forEach>--%>
<%--				</tr>--%>
<%--			</table>--%>
			<c:if test="${currentPage lt noOfPages}">
				<td><a href="${pageContext.servletContext.contextPath}/mainServlet?command=card_page_get&page=${currentPage + 1}&card_id=${card.id}">Next</a></td>
			</c:if>
		</c:if>
	</div>
</tags:general>