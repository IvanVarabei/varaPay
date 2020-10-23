<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<jsp:useBean id="account" type="com.varabei.ivan.model.entity.Account" scope="request"/>
<tags:general pageTitle="Account">
	<div class="account">
		<div class="account__info account__title">
			<div class="title ">Account №${account.id}</div>
			<div class="sub-sub-title ${account.active eq true ? 'green' : 'red'}">${account.active eq true ? 'active' : 'blocked'} ${account.balance}$</div>
		</div>
		<form method="post">
			<div class="account__buttons">
				<input type="hidden" name="account_id" value="${account.id}">
				<button class="button"
								formaction="${pageContext.servletContext.contextPath}/mainServlet?command=block_account_post"
					${account.active eq true ? '' : 'disabled'}>block
				</button>
				<button class="button"
								formaction="${pageContext.servletContext.contextPath}/mainServlet?command=top_up_amount_page_get"
					${account.active eq true ? '' : 'disabled'}>top up
				</button>
				<button class="button" ${account.active eq true ? '' : 'disabled'}>withdraw</button>
			</div>
		</form>
			<jsp:useBean id="bids" type="java.util.List" scope="request"/>
		<c:if test="${not empty bids}">
		<div class="sub-title account__sub-title">operation history</div>
		<div class="operation-header">
			<div class="operation-header__info operation__part">Operation info</div>
			<div class="operation-header__client-message operation__part">Your message</div>
			<div class="operation-header__admin-comment operation__part">Admin comment</div>
		</div>
		<c:forEach var="bid" items="${bids}">
		<div class="operation">
			<div class="operation__id operation__part">№${bid.id}</div>
			<div class="operation__state operation__part">${bid.state}</div>
			<div class="operation__type operation__part">${bid.topUp eq true ? 'top up' : 'withdraw'} ${bid.amount}$</div>
			<div class="operation__client-message operation__part">${bid.clientMessage}</div>
			<div class="operation__admin-comment operation__part">${bid.adminComment}</div>
			<div class="operation__date operation__part"><javatime:format value="${bid.placingDateTime}" style="MS"/></div>
			<div class="operation__edge operation__part"></div>
		</div>
		</c:forEach>
		</c:if>
</tags:general>