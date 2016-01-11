<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>성적 입력</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>성적 입력</h3>
	
	<form id="gradeForm" role="form" method="post" action="/school/grades/save">
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="30%">
			<col width="40%">
			<col width="30%">
		</colgroup>
		<thead>
		<tr>
			<th>과목</th>
			<th>이름</th>
			<th>점수</th>
		</tr>
		</thead>
  		<tbody>
		<c:forEach var="grade" varStatus="status" items="${gradeList}">
			<tr>
				<td><input type="text" class="form-control" name="subjectName" value="${grade.subject.subjectName}" readonly></td>
				<td><input type="text" class="form-control" name="userName" placeholder="이름입력" value="${grade.schoolUser.name}"></td>
				<td><input type="text" class="form-control" name="score" placeholder="성적입력" value="${grade.score}"></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<div>
		<button type="button" class="btn btn-info">행 추가</button>
		<button type="button" class="btn btn-danger">행 삭제</button>
		<button type="button" class="btn btn-success">일괄저장</button>
	</div>
	</form>
</div>

<script type="text" id="trObj">
<tr>
	<td><input type="text" class="form-control" name="subjectName" value="${subjectName}" readonly></td>
	<td><input type="text" class="form-control" name="userName" placeholder="이름입력" value=""></td>
	<td><input type="text" class="form-control" name="score" placeholder="성적입력" value=""></td>
</tr>
</script>

<script type="text/javascript">
//행 추가 버튼
jQuery(".btn-info").click(function(event){
	event.preventDefault();
	//마지막 행 복사해서 readonly 제외한 input 데이터 삭제 후 추가
	var trObj = jQuery("#trObj").text();
	jQuery('table > tbody:last').append(trObj);
});

//행 삭제 버튼
jQuery(".btn-danger").click(function(event){
	event.preventDefault();
	
	if(jQuery("tr").length <= 1) {
		alert("더이상 삭제될 행이 없습니다.");
	} else {
		//마지막 행 제거
		jQuery("tr").last().remove();
	}
});

//일괄저장 버튼
jQuery(".btn-success").click(function(event){
	event.preventDefault();
	
	if(jQuery("tr").length <= 1) {
		alert("한개 이상의 행이 존재하여야 합니다.");
	} else {
		jQuery("#gradeForm").submit();
	}
});
</script>
</body>
</html>