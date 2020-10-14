<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
<c:forEach var="account" items="${accounts}">
	<form method="post">
		<div class="profile__account">
			<div class="profile__account-text">№${account.id}</div>
			<div class="profile__account-text">${account.balance}$</div>
			<div class="profile__account-text">${account.active eq true ? 'active' : 'blocked'}</div>

			<input type="hidden" name="account_id" value="${account.id}">
			<input name="amount">
			<button class="button"
							formaction="${pageContext.servletContext.contextPath}/mainServlet?command=place_top_up_bid_post">top
				up
			</button>
			<button class="button"
							formaction="${pageContext.servletContext.contextPath}/mainServlet?command=block_account_post">block
			</button>
			<button class="button">delete</button>
		</div>
	</form>
	<div class="profile__cards">
		<div class="sub-sub-title profile__cards-sub-sub-title">cards</div>
		<jsp:include page="/mainServlet?command=include_cards&account_id=${account.id}"/>
		<button class="button profile__card-button">add new card</button>
	</div>
</c:forEach>