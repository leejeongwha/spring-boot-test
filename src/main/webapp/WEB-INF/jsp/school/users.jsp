<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인원 리스트</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>인원 리스트</h3>
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="10%">
			<col width="30%">
			<col width="25%">
			<col width="25%">
			<col width="10%">
		</colgroup>
		<thead>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>생년월일</th>
			<th>직업</th>
			<th>삭제</th>
		</tr>
		</thead>
  		<tbody>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td>${user.id}</td>
				<td><a href="/school/users/${user.id}">${user.name}</a></td>
				<td><fmt:formatDate value="${user.birthDate}" pattern="yyyy-MM-dd"/></td>
				<td>${user.role}</td>
				<td><button type="button" class="btn btn-danger editable" >삭제</button></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<div>
		<a href="/school/users/form" class="btn btn-info editable">인원 등록</a>
	</div>
</div>

<script type="text/javascript">
//삭제버튼
jQuery(".btn-danger").click(function(event){
	event.preventDefault();
	
	if(confirm("삭제 하시겠습니까?")) {
		var id = jQuery(this).parents("td").siblings().eq(0).html();
    	
    	$.post( "/school/users/delete/" + id, function( data ) {
    		if(data == "success") {
    			alert("삭제 완료 되었습니다.");
    		} else {
    			alert("삭제 실패 하였습니다.");
    		}
			location.reload();
    	});
	}
});
</script>
</body>
</html>