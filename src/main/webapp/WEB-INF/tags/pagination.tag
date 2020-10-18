<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="amountOfPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="url" required="true" %>

<div class="pagination pagination_margin">
	<c:if test="${currentPage gt 1}">
		<div class="pagination__item"><a href="${url}&page=${currentPage - 1}">Previous</a></div>
	</c:if>
	<c:forEach begin="${1}" end="${amountOfPages}" var="i">
		<c:choose>
			<c:when test="${i < currentPage - 2 and i eq 1}">
				<div class="pagination__item"><a href="${url}&page=${i}">${i}...</a></div>
			</c:when>
			<c:when test="${i eq amountOfPages and currentPage < i -2}">
				<div class="pagination__item"><a href="${url}&page=${i}">...${i}</a></div>
			</c:when>
			<c:when test="${i eq currentPage}">
				<div class="pagination__item pagination__current-page">${i}</div>
			</c:when>
			<c:when test="${i < currentPage - 2}">
			</c:when>
			<c:when test="${i > currentPage + 2}">
			</c:when>
			<c:otherwise>
				<div class="pagination__item"><a href="${url}&page=${i}">${i}</a></div>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${currentPage lt amountOfPages}">
		<div class="pagination__item"><a href="${url}&page=${currentPage + 1}">Next</a></div>
	</c:if>
</div>

