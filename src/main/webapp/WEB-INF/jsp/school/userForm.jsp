<%@page import="com.naver.test.school.model.Role"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인원 관리</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
	<h3>인원 관리</h3>
	
	<form id="userForm" role="form" method="post" action="/school/users/save">
	<input type="hidden" name="id" id="id" value="${user.id}">
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
				<td>이름</td>
				<td><input type="text" class="form-control" name="name" placeholder="이름입력" value="${user.name}"></td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td><input type="text" class="form-control" name="birthDate" placeholder="yyyy-MM-dd" value="<fmt:formatDate value="${user.birthDate}" pattern="yyyy-MM-dd"/>"></td>
			</tr>
			<tr>
				<td>유형</td>
				<td>
					<c:set var="enumValues" value="<%=Role.values()%>"/>
					<select name="role">
					<c:forEach items="${enumValues}" var="enumValue">
						<option value="${enumValue}" ${user.role == enumValue ? 'selected="selected"' : '' }>${enumValue}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr ${user.role != 'TEACHER' ? 'style="display:none"' : '' }>
				<td>과목</td>
				<td>
					<select name="subjectId">
					<c:forEach items="${subjectList}" var="subject">
						<option value="${subject.id}" ${user.subject.id == subject.id ? 'selected="selected"' : '' }>${subject.subjectName}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	<div>
		<button type="submit" class="btn btn-info">저장</button>
	</div>
	</form>
</div>

<script type="text/javascript">
//삭제버튼
jQuery("select[name=role]").change(function(event){
	event.preventDefault();
	
	if(jQuery('select[name=role] option:selected').val() == "TEACHER") {
		jQuery('table tr').last().show();
	} else {
		jQuery('table tr').last().hide();
	}
});
</script>
</body>
</html>