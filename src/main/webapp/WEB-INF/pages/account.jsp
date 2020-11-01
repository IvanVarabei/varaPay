<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="account.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="blocked" var="blocked"/>
	<fmt:message key="active" var="active"/>
	<fmt:message key="block_button" var="block_button"/>
	<fmt:message key="top_up" var="top_up"/>
	<fmt:message key="withdraw" var="withdraw"/>
	<fmt:message key="history" var="history"/>
	<fmt:message key="message" var="message"/>
	<fmt:message key="comment" var="comment"/>
	<fmt:message key="operation" var="operation"/>
	<fmt:message key="in_progress" var="in_progress"/>
	<fmt:message key="approved" var="approved"/>
	<fmt:message key="rejected" var="rejected"/>
</fmt:bundle>
<jsp:useBean id="account" type="com.varabei.ivan.model.entity.Account" scope="request"/>
<tags:general pageTitle="${title}">
	<div class="account">
		<div class="account__info account__title">
			<div class="title ">${title}${account.id}</div>
			<div class="sub-sub-title ${account.active eq true ? 'green' : 'red'}">${account.active eq true ? active : blocked} ${account.balance}$</div>
		</div>
		<form method="post">
			<div class="account__buttons">
				<input type="hidden" name="accountId" value="${account.id}">
				<button class="button"
								formaction="${pageContext.servletContext.contextPath}/mainServlet?command=block_account_post&page=${requestScope.currentPage}"
					${account.active eq true ? '' : 'disabled'}>${block_button}
				</button>
				<button class="button"
								formaction="${pageContext.servletContext.contextPath}/mainServlet?command=top_up_amount_page_get"
					${account.active eq true ? '' : 'disabled'}>${top_up}
				</button>
				<button class="button"
								formaction="${pageContext.servletContext.contextPath}/mainServlet?command=withdraw_amount_page_get"
					${account.active eq true ? '' : 'disabled'}>${withdraw}</button>
			</div>
		</form>
		<c:if test="${not empty requestScope.bids}">
			<jsp:useBean id="bids" type="java.util.List" scope="request"/>
			<div class="sub-title account__sub-title">${history}</div>
			<div class="operation-header">
				<div class="operation-header__info operation__part">${operation}</div>
				<div class="operation-header__client-message operation__part">${message}</div>
				<div class="operation-header__admin-comment operation__part">${comment}</div>
			</div>
			<c:forEach var="bid" items="${bids}">
				<div class="operation">
					<div class="operation__id operation__part">№${bid.id}</div>
					<div class="operation__state operation__part">${bid.state eq 'IN_PROGRESS' ? in_progress : (bid.state eq 'APPROVED' ? approved : rejected)}</div>
					<div class="operation__type operation__part">${bid.topUp eq true ? top_up : withdraw} ${bid.amount}$</div>
					<div class="operation__currency operation__part">${bid.amountInChosenCurrency} ${bid.currency.conciseName}</div>
					<div class="operation__client-message operation__part">${bid.clientMessage}</div>
					<div class="operation__admin-comment operation__part">${bid.adminComment}</div>
					<div class="operation__date operation__part"><javatime:format value="${bid.placingDateTime}"
																																				style="MS"/></div>
					<div class="operation__edge operation__part"></div>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<tags:pagination amountOfPages="${requestScope.amountOfPages}" currentPage="${requestScope.currentPage}" url="
			${pageContext.servletContext.contextPath}/mainServlet?command=account_page_get&accountId=${account.id}"/>
</tags:general>