<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Accounts">
	<div class="run-accounts">
		<div class="title run-accounts__title">run accounts</div>
		<c:if test="${not empty requestScope.accounts}">
			<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
			<div class="run-accounts__list">
				<div class="run-account__item">User login</div>
				<div class="run-account__item">User email</div>
				<div class="run-account__item">Account id</div>
				<div class="run-account__item">Action</div>
				<c:forEach var="account" items="${accounts}">
					<div class="run-account__item">${account.user.login}</div>
					<div class="run-account__item">${account.user.email}</div>
					<div class="run-account__item">${account.id}</div>
					<form class="run-account-form" method="post"
								action="${pageContext.servletContext.contextPath}/mainServlet?command=unblock_account_post">
						<input class="input" name="secret_word">
						<input type="hidden" name="account_id" value="${account.id}">
						<button class="button">enable</button>
					</form>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${empty requestScope.accounts}">
			<div class="sub-title">There are no disabled accounts</div>
		</c:if>
	</div>
</tags:general>