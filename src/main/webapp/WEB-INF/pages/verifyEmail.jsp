<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="content" prefix="verify_email.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="temp_code" var="temp_code"/>
	<fmt:message key="submit" var="submit"/>
</fmt:bundle>
<fmt:bundle basename="content" prefix="error.">
	<fmt:message key="can_not_be_empty" var="can_not_be_empty"/>
</fmt:bundle>
<c:if test="${not empty requestScope.error}">
	<fmt:bundle basename="content" prefix="error.">
		<fmt:message key="${requestScope.error}" var="fail_message"/>
	</fmt:bundle>
</c:if>
<tags:general pageTitle="${title}">
	<div class="authorization">
		<div class="authorization__title title">${title}</div>
		<form class="form" method="post"
					action="${pageContext.servletContext.contextPath}/mainServlet?command=verify_email_post">
			<p class="form__input-label">${temp_code}</p>
			<input class="input form__input" name="tempCode" value="${param.tempCode}" required
						 oninvalid="this.setCustomValidity('${can_not_be_empty}')" onchange="this.setCustomValidity('')">
			<p class="form__error">${fail_message}</p>
			<button class="button form_button">${submit}</button>
		</form>
	</div>
</tags:general>