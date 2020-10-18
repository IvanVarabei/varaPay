<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>


<jsp:useBean id="cards" type="java.util.List" scope="request"/>
<c:forEach var="card" items="${cards}">
	<a href="${pageContext.request.contextPath}/mainServlet?command=card_page_get&card_id=${card.id}"
		 class="profile__card">
		<p class="profile__card-text">${f:formatCardNumber(card.cardNumber)}</p>
		<p class="profile__card-text"><tags:localDate date="${card.validThru}" pattern="MM/yy"/></p>
		<form method="post" action="${pageContext.servletContext.contextPath}/mainServlet?command=delete_card_post">
			<input type="hidden" name="card_id" value="${card.id}">
			<button class="button">delete</button>
		</form>
	</a>
</c:forEach>