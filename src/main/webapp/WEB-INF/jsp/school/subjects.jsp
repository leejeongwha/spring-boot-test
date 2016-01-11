<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>과목 리스트</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>과목 리스트</h3>
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="10%">
			<col width="30%">
			<col width="30%">
			<col width="20%">
			<col width="10%">
		</colgroup>
		<thead>
		<tr>
			<th>아이디</th>
			<th>과목명</th>
			<th>담당선생님</th>
			<th>과목별 성적입력</th>
			<th>삭제</th>
		</tr>
		</thead>
  		<tbody>
		<c:forEach var="subject" items="${subjectList}">
			<tr>
				<td>${subject.id}</td>
				<td><a href="/school/subjects/${subject.id}">${subject.subjectName}</a></td>
				<td>
				<c:forEach var="user" items="${subject.schoolUsers}">
				${user.name},
				</c:forEach>
				</td>
				<td><a href="/school/subjects/${subject.id}/grades" class="btn btn-info editable">성적입력</a></a></td>
				<td><button type="button" class="btn btn-danger editable" >삭제</button></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<div>
		<a href="/school/subjects/form" class="btn btn-info editable">과목 등록</a>
	</div>
</div>

<script type="text/javascript">
//삭제버튼
jQuery(".btn-danger").click(function(event){
	event.preventDefault();
	
	if(confirm("삭제 하시겠습니까?")) {
		var id = jQuery(this).parents("td").siblings().eq(0).html();
    	
    	$.post( "/school/subjects/delete/" + id, function( data ) {
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