<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<!DOCTYPE html>
<html lang="ru">
<head>
	<title>${pageTitle}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nullStile.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
							<a href="${pageContext.request.contextPath}" class="header__link">Welcome page</a>
						</li>
						<c:if test="${not empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=profile_get"
									 class="header__link">Profile</a>
							</li>
						</c:if>
						<c:if test="${sessionScope.role_name eq 'admin'}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=run_accounts_get" class="header__link">Run
									accounts</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=run_bids_get" class="header__link">Run
									bids</a>
							</li>
						</c:if>
						<c:if test="${empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=signup_get"
									 class="header__link">Signup</a>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=login_get"
									 class="header__link">Login</a>
							</li>
						</c:if>
						<c:if test="${not empty sessionScope.login}">
							<li>
								<a href="${pageContext.request.contextPath}/mainServlet?command=logout" class="header__link">Logout</a>
							</li>
						</c:if>
						<li>
							<form action="${pageContext.request.contextPath}/mainServlet" method="post">
								<select name="locale" onchange="this.form.submit()">
						<li>
							<option value="en-US" ${sessionScope.locale.language eq 'en' ? 'selected' : ''}>EN</option>
							<option value="ru-RU" ${sessionScope.locale.language eq 'ru' ? 'selected' : ''}>RU</option>
						</li>
						</select>
						<c:set var="maeth" value="<%= request.getMethod() %>"/>
						<input type="hidden" name="previousMethod" value="${maeth}">
						<input type="hidden" name="command" value="change_language">
						<c:forEach var="currentParam" items="${param}">
							<input type="hidden" name="${currentParam.key}" value="${currentParam.value}">
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
					Currency rates
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
		<div class="footer__copy">Copy 2020</div>
		<div class="footer__text">Lorem ipsum dolor sit amet.</div>
	</footer>
</div>
<script src="js/script.js"></script>
</body>
</html>