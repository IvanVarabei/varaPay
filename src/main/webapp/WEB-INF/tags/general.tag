<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="header.">
	<fmt:message key="welcome" var="welcome"/>
	<fmt:message key="signup" var="signup"/>
	<fmt:message key="login" var="login"/>
	<fmt:message key="logout" var="logout"/>
	<fmt:message key="profile" var="profile"/>
	<fmt:message key="run_bids" var="run_bids"/>
	<fmt:message key="run_accounts" var="run_accounts"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="sidebar.">
	<fmt:message key="currency_rates" var="currency_rates"/>
</fmt:bundle>
<!DOCTYPE html>
<html lang="ru">
<head>
	<title>${pageTitle}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nullStile.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico"/>
</head>
<body>
<div class="wrapper">
	<header class="header">
		<div class="container">
			<div class="header__body">
				<div class="header__logo-title">
					<a href="" class="header__logo">
						<img src="img/logo.jpg">
					</a>
					<p class="header__title">VaraPay</p>
				</div>
				<div class="header__burger">
					<span></span>
				</div>
				<nav class="header__menu">
					<ul class="header__list">
						<li>
							<p class="header__link">+375 29 7324595</p>
						</li>
						<c:if test="${empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}" class="header__link">${welcome}</a>
							</li>
						</c:if>
						<c:if test="${not empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=profile_get"
									 class="header__link">${profile}</a>
							</li>
						</c:if>
						<c:if test="${sessionScope.roleName eq 'admin'}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=run_accounts_get"
									 class="header__link">${run_accounts}</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=run_bids_get"
									 class="header__link">${run_bids}</a>
							</li>
						</c:if>
						<c:if test="${empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=signup_get"
									 class="header__link">${signup}</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=login_get"
									 class="header__link">${login}</a>
							</li>
						</c:if>
						<c:if test="${not empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=logout"
									 class="header__link">${logout}</a>
							</li>
						</c:if>
						<li>
							<form id="change_lang" action="${pageContext.request.contextPath}/mainServlet" method="post">
								<select form="change_lang" name="locale" onchange="this.form.submit()">
						<li>
							<option value="en-US" ${sessionScope.locale.language eq 'en' ? 'selected' : ''}>EN</option>
							<option value="ru-RU" ${sessionScope.locale.language eq 'ru' ? 'selected' : ''}>RU</option>
						</li>
						</select>
						<input type="hidden" name="command" value="change_language_post">
						<c:forEach var="currentParam" items="${param}">
							<c:choose>
								<c:when test="${currentParam.value eq 'card_page_get' and param.cvc != null}">
									<input type="hidden" name="${currentParam.key}" value="make_payment_post">
								</c:when>
								<c:when test="${(currentParam.value eq 'top_up_message_page_get'
								or currentParam.value eq 'withdraw_message_page_get') and param.amountInChosenCurrency != null}">
									<input type="hidden" name="${currentParam.key}" value="place_bid_post">
								</c:when>
								<c:otherwise>
									<input type="hidden" name="${currentParam.key}" value="${currentParam.value}">
								</c:otherwise>
							</c:choose>
						</c:forEach>
						</form>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</header>
	<main class="main">
		<aside class="sidebar">
			<nav class="sidebar__menu">
				<div class="sub-title sidebar__title">
					${currency_rates}
				</div>
				<ul>
					<jsp:include page="/mainServlet?command=include_currencies"/>
				</ul>
			</nav>
		</aside>
		<section class="content">
			<jsp:doBody/>
		</section>
	</main>
	<footer class="footer">
		<div class="footer__copy">© 2020 Ivan Varabei</div>
		<div class="footer__text">varabeiivan@gmail.com</div>
	</footer>
</div>
<script src="js/burgerMenu.js"></script>
</body>
</html>