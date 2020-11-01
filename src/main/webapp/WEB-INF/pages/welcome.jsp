<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="welcome.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="we_offer" var="we_offer"/>
	<fmt:message key="list_item_1" var="list_item_1"/>
	<fmt:message key="list_item_2" var="list_item_2"/>
	<fmt:message key="list_item_3" var="list_item_3"/>
	<fmt:message key="list_item_4" var="list_item_4"/>
	<fmt:message key="list_item_5" var="list_item_5"/>
	<fmt:message key="signup" var="signup"/>
	<fmt:message key="login" var="login"/>
</fmt:bundle>
<tags:general pageTitle="VARAPAY®">
	<div class="welcome">
		<div class="title welcome__title">${title}</div>
		<div class="sub-title welcome__sub-title">${we_offer}</div>
		<ul class="list">
			<li class="sub-sub-title">${list_item_1}</li>
			<li class="sub-sub-title">${list_item_2}</li>
			<li class="sub-sub-title">${list_item_3}</li>
			<li class="sub-sub-title">${list_item_4}</li>
			<li class="sub-sub-title">${list_item_5}</li>
		</ul>
		<div class="welcome__buttons">
			<a href="${pageContext.request.contextPath}/mainServlet?command=signup_get" class="welcome__link">
				<button class="button">${signup}</button>
			</a>
			<a href="${pageContext.request.contextPath}/mainServlet?command=login_get" class="welcome__link">
				<button class="button">${login}</button>
			</a>
		</div>
	</div>
</tags:general>