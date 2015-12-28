<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Notice List</title>
</head>
<body>
<h1><spring:message code='notice.head'/></h1>
<div>
	<table border="1" width="40%">
		<colgroup>
			<col width="10%">
			<col width="70%">
			<col width="20%">
		</colgroup>
		<tr>
			<th><spring:message code='notice.seq'/></th>
			<th><spring:message code='notice.title'/></th>
			<th><spring:message code='notice.user'/></th>
		</tr>
		<c:forEach var="notice" items="${noticeList}">
			<tr>
				<td>${notice.seq}</td>
				<td><a href="/notice/${notice.seq}">${notice.title}</a></td>
				<td>${notice.userId}</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<div>
		<a href="/notice/form"><spring:message code='notice.new'/></a>
	</div>
</div>
</body>
</html>