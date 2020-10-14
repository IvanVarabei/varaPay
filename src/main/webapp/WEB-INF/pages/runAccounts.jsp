<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Accounts">
	<c:if test="${not empty requestScope.accounts}">
		<jsp:useBean id="accounts" type="java.util.List" scope="request"/>
	</c:if>
	<div>
		<div class="title">Accounts</div>
		<c:forEach var="account" items="${accounts}">
			<form method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=unblock_account_post">
				<input type="hidden" name="account_id" value="${account.id}">
			${account.id}<button>unblock</button>
				<br>
				<hr>
			</form>
		</c:forEach>
	</div>
</tags:general>