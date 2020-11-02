<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="top_up.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="message" var="message"/>
	<fmt:message key="text_part_1_1" var="text_part_1_1"/>
	<fmt:message key="text_part_1_2" var="text_part_1_2"/>
	<fmt:message key="text_part_1_3" var="text_part_1_3"/>
	<fmt:message key="text_part_2" var="text_part_2"/>
	<fmt:message key="text_part_3" var="text_part_3"/>
	<fmt:message key="submit" var="submit"/>
	<fmt:message key="fail_message" var="fail_message"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="can_not_be_empty" var="can_not_be_empty"/>
</fmt:bundle>
<jsp:useBean id="accountId" type="java.lang.Long" scope="request"/>
<jsp:useBean id="amount" type="java.math.BigDecimal" scope="request"/>
<jsp:useBean id="amountInChosenCurrency" type="java.math.BigDecimal" scope="request"/>
<jsp:useBean id="currency" type="com.varabei.ivan.model.entity.CustomCurrency" scope="request"/>
<tags:general pageTitle="${title}">
	<div class="bid-message">
		<div class="title bid-message__title">${title} ${amount}$</div>
		<div class="steps">
			<div class="step">
				1. ${text_part_1_1} <b>${currency}</b> ${text_part_1_2} ${currency} ${text_part_1_3}
				<div class="sub-step green">${requestScope.amountInChosenCurrency} <b>${currency}</b></div>
				<div class="sub-step green">${currency.wallet}</div>
			</div>
			<div class="step">2. ${text_part_2}</div>
			<div class="step">3. ${text_part_3}</div>
		</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=place_bid_post">
			<p class="form__input-label">${message}</p>
			<input type="text" name="clientMessage" class="input form__input" required
						 oninvalid="this.setCustomValidity('${can_not_be_empty}')" onchange="this.setCustomValidity('')">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${fail_message}</p></c:if>
			<input type="hidden" name="isTopUp" value="true">
			<input type="hidden" name="accountId" value="${accountId}">
			<input type="hidden" name="amount" value="${amount}">
			<input type="hidden" name="currency" value="${currency}">
			<input type="hidden" name="amountInChosenCurrency" value="${amountInChosenCurrency}">
			<button class="button form_button">${submit}</button>
		</form>
	</div>
</tags:general>