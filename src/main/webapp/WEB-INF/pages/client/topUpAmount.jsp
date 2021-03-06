<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="top_up.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="amount" var="amount"/>
	<fmt:message key="currency_input" var="currency_input"/>
	<fmt:message key="continue_button" var="continue_button"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="can_not_be_empty" var="can_not_be_empty"/>
	<fmt:message key="amount" var="client_amount_error"/>
</fmt:bundle>
<c:if test="${not empty requestScope.errors}">
	<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	<fmt:bundle basename="content" prefix="error.">
		<c:if test="${errors.amount != param.amount}">
			<fmt:message key="${errors.amount}" var="amount_error"/>
		</c:if>
		<c:if test="${errors.currency != param.currency}">
			<fmt:message key="${errors.currency}" var="currency_error"/>
		</c:if>
	</fmt:bundle>
</c:if>
<jsp:useBean id="accountId" type="java.lang.Long" scope="request"/>
<tags:general pageTitle="${title}">
	<div class="bid-amount">
		<div class="title bid-amount__title">${title}</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=top_up_message_page_get">
			<input type="hidden" name="accountId" value="${accountId}">

			<p class="form__input-label">${amount} $</p>
			<input type="number" name="amount" class="input form__input" value="${not empty param.amount ? param.amount : ''}"
						 step="0.01" min="0.01" maxlength="7" required
						 oninvalid="this.setCustomValidity('${client_amount_error}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${amount_error}</p>

			<p class="form__input-label">${currency_input}</p>
			<select class="input" name="currency" required oninvalid="this.setCustomValidity('${can_not_be_empty}')"
							onchange="this.setCustomValidity('')">
				<option>${param.currency}</option>
				<c:forEach var="currency" items="${requestScope.currencies}">
					<c:if test="${currency != param.currency}">
						<option>${currency}</option>
					</c:if>
				</c:forEach>
			</select>
			<p class="form__error">${currency_error}</p>

			<button class="button form_button">${continue_button}</button>
		</form>
	</div>
</tags:general>