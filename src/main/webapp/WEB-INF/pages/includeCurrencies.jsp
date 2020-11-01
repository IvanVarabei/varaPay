<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<jsp:useBean id="currencies" type="java.util.List" scope="request"/>
<c:forEach var="currency" items="${currencies}">
	<li>
		<div class="currency">
			<div class="currency__image">
				<img src="${currency.img}" alt="">
			</div>
			<div class="currency__name">${currency.name()}</div>
			<div class="currency__concise">${currency.conciseName}</div>
			<div class="currency__cost">$<fmt:formatNumber value="${currency.cost}" minFractionDigits="0"/></div>
		</div>
	</li>
</c:forEach>