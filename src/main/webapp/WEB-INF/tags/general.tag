<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>${pageTitle}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nullStile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
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
                <li>
                    <a href="${pageContext.request.contextPath}/mainServlet?command=register" class="header__link">Create account</a>
                </li>
                <li>
                    <a href="" class="header__link">Login</a>
                </li>
                <li>
                    <a href="" class="header__link">Logout</a>
                </li>
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