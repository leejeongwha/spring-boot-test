<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notice Detail</title>
</head>
<body>
<h1><spring:message code='notice.detail.head'/></h1>
<div>
	<form name="frmNotice" method="post" action="/notice/save">
		<c:if test="${not empty notice}">
			<input type="hidden" name="seq" value="${notice.seq}" />
		</c:if>
		
		<table width="50%">
			<colgroup>
				<col width="10%">
				<col width="90%">
			</colgroup>
			<tr>
				<td><spring:message code='notice.title'/> :</td>
				<td><input type="text" name="title" maxlength='80' size='80' value="${notice.title}" /></td>
			</tr>
			<tr>
				<td><spring:message code='notice.content'/> :</td>
				<td><textarea name="content" cols="80" rows="10">${notice.content}</textarea></td>
			</tr>
		</table>
		
		<button type="submit"><spring:message code='notice.submit'/></button>
	</form>
</div>
</body>
</html>