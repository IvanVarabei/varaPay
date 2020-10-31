<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="run_bids.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="bid" var="bid"/>
	<fmt:message key="top_up" var="top_up"/>
	<fmt:message key="withdraw" var="withdraw"/>
	<fmt:message key="message" var="message"/>
	<fmt:message key="comment" var="comment"/>
	<fmt:message key="approve" var="approve"/>
	<fmt:message key="reject" var="reject"/>
	<fmt:message key="no_bids" var="no_bids"/>
</fmt:bundle>
<tags:general pageTitle="${title}">
	<div class="run-bids">
		<div class="title run-bids__title">${title}</div>
		<c:if test="${not empty requestScope.bids}">
			<jsp:useBean id="bids" type="java.util.List" scope="request"/>
			<div class="run-bids__header">
				<div class="run-bids__item">${bid}</div>
				<div class="run-bids__item">${message}</div>
				<div class="run-bids__item">${comment}</div>
			</div>
			<c:forEach var="bid" items="${bids}">
				<div class="run-bids__row">
					<div class="run-bids__id run-bids__item">№${bid.id}</div>
					<div class="run-bids__operation-type run-bids__item">${bid.topUp eq true ? top_up : withdraw} ${bid.amount}$</div>
					<div class="run-bids__currency run-bids__item">${bid.amountInChosenCurrency} ${bid.currency.conciseName}</div>
					<div class="run-bids__date run-bids__item">
						<javatime:format value="${bid.placingDateTime}" style="MS"/></div>
					<div class="run-bids__user-message run-bids__item">${bid.clientMessage}</div>
					<div class="run-bids__email run-bids__item">${bid.account.user.email}</div>
					<form class="run-bids__form run-bids__item" method="post">
						<input type="hidden" name="bid_id" value="${bid.id}">
						<textarea name="admin_comment"></textarea>
						<button class="button"
										formaction="${pageContext.servletContext.contextPath}/mainServlet?command=reject_top_up_bid_post&page=
										${requestScope.currentPage eq requestScope.amountOfPages ?
										 (bids.size() > 1 ? requestScope.currentPage : requestScope.currentPage-1)
										 : requestScope.currentPage}">
								${reject}
						</button>
						<button class="button"
										formaction="${pageContext.servletContext.contextPath}/mainServlet?command=approve_top_up_bid_post&page=
										${requestScope.currentPage eq requestScope.amountOfPages ?
										 (bids.size() > 1 ? requestScope.currentPage : requestScope.currentPage-1)
										 : requestScope.currentPage}">
								${approve}
						</button>
					</form>
					<div class="run-bids__edge run-bids__item"></div>
				</div>
			</c:forEach>

		</c:if>
		<c:if test="${empty requestScope.bids}">
			<div class="sub-title">${no_bids}</div>
		</c:if>
	</div>
	<tags:pagination amountOfPages="${requestScope.amountOfPages}" currentPage="${requestScope.currentPage}" url="
			${pageContext.servletContext.contextPath}/mainServlet?command=run_bids_get"/>
</tags:general>

<%--<tags:general pageTitle="${title}">--%>
<%--	<div class="run-bids">--%>
<%--		<div class="title run-bids__title">${title}</div>--%>
<%--		<c:if test="${not empty requestScope.bids}">--%>
<%--			<jsp:useBean id="bids" type="java.util.List" scope="request"/>--%>
<%--			<div class="run-bids__header">--%>
<%--				<div class="run-bids__item">${bid}</div>--%>
<%--				<div class="run-bids__item">${message}</div>--%>
<%--				<div class="run-bids__item">${comment}</div>--%>
<%--			</div>--%>
<%--			<c:forEach var="bid" items="${bids}">--%>
<%--				<div class="run-bids__row">--%>
<%--					<div class="run-bids__id run-bids__item">№${bid.id}</div>--%>
<%--					<div class="run-bids__operation-type run-bids__item">${bid.topUp eq true ? top_up : withdraw} ${bid.amount}$</div>--%>
<%--					<div class="run-bids__email run-bids__item">${bid.account.user.email}</div>--%>
<%--					<div class="run-bids__date run-bids__item">--%>
<%--						<javatime:format value="${bid.placingDateTime}" style="MS"/></div>--%>
<%--					<div class="run-bids__user-message run-bids__item">${bid.clientMessage}</div>--%>
<%--					<form class="run-bids__form run-bids__item" method="post">--%>
<%--						<input type="hidden" name="bid_id" value="${bid.id}">--%>
<%--						<textarea name="admin_comment"></textarea>--%>
<%--						<button class="button"--%>
<%--										formaction="${pageContext.servletContext.contextPath}/mainServlet?command=reject_top_up_bid_post&page=--%>
<%--										${requestScope.currentPage eq requestScope.amountOfPages ?--%>
<%--										 (bids.size() > 1 ? requestScope.currentPage : requestScope.currentPage-1)--%>
<%--										 : requestScope.currentPage}">--%>
<%--							${reject}--%>
<%--						</button>--%>
<%--						<button class="button"--%>
<%--										formaction="${pageContext.servletContext.contextPath}/mainServlet?command=approve_top_up_bid_post&page=--%>
<%--										${requestScope.currentPage eq requestScope.amountOfPages ?--%>
<%--										 (bids.size() > 1 ? requestScope.currentPage : requestScope.currentPage-1)--%>
<%--										 : requestScope.currentPage}">--%>
<%--							${approve}--%>
<%--						</button>--%>
<%--					</form>--%>
<%--					<div class="run-bids__edge run-bids__item"></div>--%>
<%--				</div>--%>
<%--			</c:forEach>--%>

<%--		</c:if>--%>
<%--		<c:if test="${empty requestScope.bids}">--%>
<%--			<div class="sub-title">${no_bids}</div>--%>
<%--		</c:if>--%>
<%--	</div>--%>
<%--	<tags:pagination amountOfPages="${requestScope.amountOfPages}" currentPage="${requestScope.currentPage}" url="--%>
<%--			${pageContext.servletContext.contextPath}/mainServlet?command=run_bids_get"/>--%>
<%--</tags:general>--%>