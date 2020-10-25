<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
<c:forEach var="account" items="${accounts}">
	<form method="post">
		<a class="profile__account" href="${pageContext.servletContext.contextPath
		}/mainServlet?command=account_page_get&account_id=${account.id}">
			<div class="profile__account-text">№${account.id}</div>
			<div class="profile__account-text">${account.balance}$</div>
			<div class="profile__account-text  ${account.active eq true ? 'green' : 'red'}">${account.active eq true ? 'active' : 'blocked'}</div>
			<input type="hidden" name="account_id" value="${account.id}">
			<button class="button" ${account.balance > 0 ? 'disabled' : ''} formaction="${pageContext.servletContext.contextPath}/mainServlet?command=delete_account_post">
				delete
			</button>
		</a>
	</form>
	<div class="profile__cards">
		<div class="sub-sub-title profile__cards-sub-sub-title">cards</div>
		<jsp:include page="/mainServlet?command=include_cards&account_id=${account.id}"/>
		<form class="profile__card-button" method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=create_card_post">
			<input type="hidden" name="account_id" value="${account.id}"/>
			<button class="button">add new card</button>
		</form>
	</div>
</c:forEach>