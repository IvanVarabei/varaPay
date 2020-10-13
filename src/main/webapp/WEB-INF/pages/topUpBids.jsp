<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:general pageTitle="Top up bids">
	<c:if test="${not empty requestScope.accounts}">
		<jsp:useBean id="bids" type="java.util.List" scope="request"/>
	</c:if>
	<div>departure
		<div class="title">Accounts</div>
		<c:forEach var="bid" items="${bids}">
			<form method="post">
				<input type="hidden" name="bid_id" value="${bid.id}">
					${bid.id} ${bid.amount}
				<button formaction="${pageContext.servletContext.contextPath}/mainServlet?command=approve_top_up_bid">
					approve
				</button>
				<button formaction="${pageContext.servletContext.contextPath}/mainServlet?command=reject_top_up_bid">
					reject
				</button>
				<br>
				<hr>
			</form>
		</c:forEach>
	</div>
</tags:general>