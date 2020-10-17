<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="user" type="com.varabei.ivan.model.entity.User" scope="request"/>
<tags:general pageTitle="Cabinet">
	<div class="profile">
		<div class="title">Your profile</div>
		<div class="profile__info">
			<div class="profile__photo">
				<img src="img/silhouette.svg" alt="">
			</div>
			<div class="profile__text">${user.login}</div>
			<div class="profile__text">${user.firstName}</div>
			<div class="profile__text">${user.lastName}</div>
			<div class="profile__text">${user.email}</div>
			<div class="profile__text">${user.birth}</div>
			<div class="profile__text"><a href="${pageContext.servletContext.contextPath}/mainServlet?command=change_password_get">change password</a></div>
		</div>
		<div class="sub-title">Accounts</div>
		<div class="profile__accounts">
			<jsp:include page="/mainServlet?command=include_accounts&user_id=${user.id}"/>
			<form method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=create_account_post">
				<input type="hidden" name="user_id" value="${user.id}"/>
			<button class="button">add new account</button>
			</form>
		</div>
	</div>
</tags:general>