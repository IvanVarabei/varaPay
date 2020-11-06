<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="profile.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="change_password" var="change_password"/>
	<fmt:message key="accounts" var="accounts"/>
	<fmt:message key="add_account" var="add_account"/>
	<fmt:message key="card_created" var="card_created"/>
	<fmt:message key="your_cvc" var="your_cvc"/>
	<fmt:message key="cvc_attention" var="cvc_attention"/>
	<c:if test="${not empty requestScope.error}">
		<fmt:message key="cannot_be_deleted" var="cannot_be_deleted"/>
		<fmt:message key="${requestScope.error}" var="fail_message"/>
	</c:if>
</fmt:bundle>
<jsp:useBean id="user" type="com.epam.varapay.model.entity.User" scope="request"/>
<tags:general pageTitle="${title}">
	<div class="profile">
		<div class="title">${title}</div>
		<div class="profile__info">
			<div class="profile__photo">
				<img src="img/method-draw-image.svg" alt="">
			</div>
			<div class="profile__text">${user.login}</div>
			<div class="profile__text">${user.firstName} ${user.lastName}</div>
			<div class="profile__text">${user.email}</div>
			<div class="profile__text">${user.birth}</div>
			<div class="profile__text"><a
							href="${pageContext.servletContext.contextPath}/mainServlet?command=change_password_get"><b>${change_password}</b></a>
			</div>
		</div>
		<div class="sub-title">${accounts}</div>
		<div class="profile__accounts">
			<jsp:include page="/mainServlet?command=include_accounts&login=${user.login}"/>
			<form method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=create_account_post">
				<input type="hidden" name="userId" value="${user.id}"/>
				<button class="button">${add_account}</button>
			</form>
		</div>
		<div class="popup ${not empty requestScope.error ? 'popup_visible' : ''}">
			<div class="popup__body">
				<div class="popup__content">
					<a href="${pageContext.servletContext.contextPath}/mainServlet?command=profile_get" class="popup__close">X</a>
					<div class="popup__title">${cannot_be_deleted}</div>
					<div class="popup__text">${fail_message}</div>
				</div>
			</div>
		</div>
		<div class="popup ${not empty param.cvc ? 'popup_visible' : ''}">
			<div class="popup__body">
				<div class="popup__content">
					<a href="${pageContext.servletContext.contextPath}/mainServlet?command=profile_get" class="popup__close">X</a>
					<div class="popup__title">${card_created}</div>
					<div class="popup__text">${your_cvc} <b>${param.cvc}</b>. ${cvc_attention}</div>
				</div>
			</div>
		</div>
	</div>
</tags:general>