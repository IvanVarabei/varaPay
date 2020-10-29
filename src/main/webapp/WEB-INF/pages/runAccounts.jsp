<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="run_accounts.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="account_id_or_login" var="account_id_or_login"/>
	<fmt:message key="find_blocked" var="find_blocked"/>
	<fmt:message key="no_accounts" var="no_accounts"/>
	<fmt:message key="login" var="login"/>
	<fmt:message key="email" var="email"/>
	<fmt:message key="account_id" var="account_id"/>
	<fmt:message key="secret_word" var="secret_word"/>
	<fmt:message key="enable" var="enable"/>
</fmt:bundle>
<tags:general pageTitle="${title}">
	<div class="run-accounts">
		<div class="title run-accounts__title">${title}</div>
		<form class="form run-accounts__search-form" action="${pageContext.servletContext.contextPath}/mainServlet">
			<input type="hidden" name="command" value="run_accounts_get">
			<p class="form__input-label">${account_id_or_login}</p><input class="input form__input" name="query" value="${param.query}">
			<button class="button form_button">${find_blocked}</button>
		</form>
		<c:if test="${not empty requestScope.accounts}">
			<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
			<div class="run-accounts__list">
				<div class="run-account__item">${login}</div>
				<div class="run-account__item">${email}</div>
				<div class="run-account__item">${account_id}</div>
				<div class="run-account__item">${secret_word}</div>
				<c:forEach var="account" items="${accounts}">
					<div class="run-account__item">${account.user.login}</div>
					<div class="run-account__item">${account.user.email}</div>
					<div class="run-account__item">${account.id}</div>
					<form class="run-account-form" method="post"
								action="${pageContext.servletContext.contextPath}/mainServlet?command=unblock_account_post&query=${param.query}">
						<input class="input" name="secret_word">
						<c:if test="${not empty requestScope.error and account.id eq param.account_id}"><p class="form__error">${requestScope.error}</p></c:if>
						<input type="hidden" name="account_id" value="${account.id}">
						<button class="button">${enable}</button>
					</form>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${empty requestScope.accounts and param.query != null}">
			<div class="sub-title">${no_accounts}</div>
		</c:if>
	</div>
</tags:general>