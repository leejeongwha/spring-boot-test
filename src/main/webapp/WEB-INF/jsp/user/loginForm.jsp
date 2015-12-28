<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<h1><spring:message code='login.title'/></h1>
<form name="frmUser" action="/login/submit" method="post">
	<table>
		<tr>
			<td>
				<spring:message code='login.id'/> :
			</td>
			<td>
				<input name="id">
			</td>
		</tr>
		<tr>
			<td>
				<spring:message code='login.passwd'/> :
			</td>
			<td>
				<input name="passwd">
			</td>
		</tr>
	</table>
	<button type="submit"><spring:message code='login.submit'/></button>
</form>
</body>
</html>