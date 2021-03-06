<%@page import="com.naver.test.school.model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>과목 관리</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
	<h3>과목 관리</h3>
	
	<form id="userForm" role="form" method="post" action="/school/subjects/save">
	<input type="hidden" name="id" id="id" value="${subject.id}">
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="10%">
			<col width="90%">
		</colgroup>
		<thead>
		<tr>
			<th>항목</th>
			<th>값</th>
		</tr>
		</thead>
  		<tbody>
			<tr>
				<td>과목명</td>
				<td><input type="text" class="form-control" name="subjectName" placeholder="과목명입력" value="${subject.subjectName}"></td>
			</tr>
		</tbody>
	</table>
	<br>
	<div>
		<button type="submit" class="btn btn-info">저장</button>
	</div>
	</form>
</div>
</body>
</html>