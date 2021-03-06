<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="profile.">
	<fmt:message key="active" var="active"/>
	<fmt:message key="blocked" var="blocked"/>
	<fmt:message key="cards" var="cards"/>
	<fmt:message key="delete" var="delete"/>
	<fmt:message key="add_card" var="add_card"/>
</fmt:bundle>
<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
<c:forEach var="account" items="${accounts}">
	<form method="post">
		<a class="profile__account"
			 href="${pageContext.servletContext.contextPath}/mainServlet?command=account_page_get&accountId=${account.id}">
			<div class="profile__account-text">№${account.id}</div>
			<div class="profile__account-text">${account.balance}$</div>
			<div class="profile__account-text  ${account.active eq true ? 'green' : 'red'}">${account.active eq true ? active : blocked}</div>
			<input type="hidden" name="accountId" value="${account.id}">
			<button class="button"
							formaction="${pageContext.servletContext.contextPath}/mainServlet?command=delete_account_post">
					${delete}
			</button>
		</a>
	</form>
	<div class="profile__cards">
		<div class="sub-sub-title profile__cards-sub-sub-title">${cards}</div>
		<c:import url="/mainServlet?command=include_cards&accountId=${account.id}"/>
		<form class="profile__card-button" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=create_card_post">
			<input type="hidden" name="accountId" value="${account.id}"/>
			<button class="button" ${account.active ? '' : 'disabled'}>${add_card}</button>
		</form>
	</div>
</c:forEach>