<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="account_id" type="java.lang.Long" scope="request"/>
<tags:general pageTitle="Top up amount">
	<c:if test="${not empty requestScope.errors}">
		<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
	</c:if>
	<div class="topup1">
		<div class="title topup1__title">top up</div>
		<form class="form" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=top_up_page_get">
			<input type="hidden" name="account_id" value="${account_id}">
			<p class="form__input-label">You want to add</p><input type="text" name="amount" class="input form__input" value="${not empty param.amount ? param.amount : ''}">
			<p class="form__error">${errors.amount}</p>
			<p class="form__input-label">Choose currency</p>
			<select class="input" name="currency">
				<option>${param['currency']}</option>
				<c:forEach var="currency" items="${requestScope.currencies}">
					<c:if test="${currency != param['currency']}">
						<option>${currency}</option>
					</c:if>
				</c:forEach>
			</select>
			<p class="form__error">${errors.currency}</p>
			<button class="button form_button">Continue</button>
		</form>
	</div>
</tags:general>