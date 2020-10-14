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
		<a href="" class="header__logo"></a>
		<nav class="header__menu">
			<ul class="header__list">
				<li>
					<a href="${pageContext.request.contextPath}" class="header__link">Welcome page</a>
				</li>
				<c:if test="${not empty sessionScope.user_id}">
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=profile_get" class="header__link">Profile</a>
					</li>
				</c:if>
				<c:if test="${sessionScope.role_name eq 'админ'}">
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=run_accounts_get" class="header__link">Run accounts</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=run_bids_get" class="header__link">Run top up bids</a>
					</li>
				</c:if>
				<c:if test="${empty sessionScope.user_id}">
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=signup_get" class="header__link">Create
							account</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=login_get" class="header__link">Login</a>
					</li>
				</c:if>
				<c:if test="${not empty sessionScope.user_id}">
					<li>
						<a href="${pageContext.request.contextPath}/mainServlet?command=logout" class="header__link">Logout</a>
					</li>
				</c:if>
				<li>
					<a href="" class="header__link">EN / RU</a>
				</li>
			</ul>
			<div class="header__burger">
				<span></span>
			</div>
		</nav>
	</header>
	<main class="main">
		<aside class="sidebar">
			<nav class="sidebar__menu">
				<ul>
					<li>
						<a href="" class="sidebar__link">Menu punkt</a>
					</li>
					<li>
						<a href="" class="sidebar__link">Menu punkt</a>
					</li>
					<li>
						<a href="" class="sidebar__link">Menu punkt</a>
					</li>
					<li>
						<a href="" class="sidebar__link">Menu punkt</a>
					</li>
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
</body>
</html>