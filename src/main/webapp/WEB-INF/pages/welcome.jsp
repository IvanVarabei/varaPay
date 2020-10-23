<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Welcome">
<div class="welcome">
	<div class="title">Your VARAPAY® Account!</div>
	<div class="welcome__counter-grid">
		<div class="welcome__counter welcome__counter_glowing">${requestScope.userAmount}</div><div class="welcome__counter title">sutisfied participants</div>
<%--		<div class="welcome__counter welcome__counter_glowing">${requestScope.paymentAmount}</div><div class="welcome__counter title">payments`ve been sent</div>--%>
	</div>
<%--	<section class="welcome__products products">--%>
<%--		<div class="products__items">--%>
<%--			<div class="product_item">--%>
<%--				<a href="" class="products_image">--%>
<%--					<img src="img/wallet.svg" alt="">--%>
<%--				</a>--%>
<%--				<a class="products__name">Find out where you can pay for various goods and services with Varapay®."</a>--%>
<%--			</div>--%>
<%--			<div class="product_item">--%>
<%--				<a href="" class="products_image">--%>
<%--					<img src="img/api.svg" alt="">--%>
<%--				</a>--%>
<%--				<a class="products__name">Connect your website to Varapay® and accept payments from millions of customers all over the world."</a>--%>
<%--			</div>--%>
<%--			<div class="product_item">--%>
<%--				<a href="" class="products_image">--%>
<%--					<img src="img/card.svg" alt="">--%>
<%--				</a>--%>
<%--				<a class="products__name">You can add funds to Varapay® and withdraw them with numerous international methods."</a>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--	</section>--%>
</div>
</tags:general>