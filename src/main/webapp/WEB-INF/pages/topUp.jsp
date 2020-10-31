<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="account_id" type="java.lang.Long" scope="request"/>
<jsp:useBean id="amount" type="java.math.BigDecimal" scope="request"/>
<jsp:useBean id="amountInChosenCurrency" type="java.math.BigDecimal" scope="request"/>
<jsp:useBean id="currency" type="com.varabei.ivan.model.entity.Currency" scope="request"/>
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
<tags:general pageTitle="${title}">
	<div class="topup2">
		<div class="title topup2__title">${title}</div>
		<div class="steps">
			<div class="step">
				1. ${text_part_1_1} <b>${currency}</b> ${text_part_1_2} ${currency} ${text_part_1_3}
				<div class="sub-step green">${requestScope.amountInChosenCurrency} ${currency}</div>
				<div class="sub-step green">${currency.wallet}</div>
			</div>
			<div class="step">2. ${text_part_2}</div>
			<div class="step">3. ${text_part_3}</div>
		</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=PLACE_TOP_UP_BID_POST">
			<p class="form__input-label">${message}</p><input type="text" name="client_message" class="input form__input">
			<c:if test="${not empty requestScope.error}"><p class="form__error">${fail_message}</p></c:if>
			<input type="hidden" name="account_id" value="${account_id}">
			<input type="hidden" name="amount" value="${amount}">
			<input type="hidden" name="currency" value="${currency}">
			<input type="hidden" name="amountInChosenCurrency" value="${amountInChosenCurrency}">
			<button class="button form_button">${submit}</button>
		</form>
	</div>
</tags:general>