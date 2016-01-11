<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>성적 리스트</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>성적 리스트</h3>
	
	<form id="form_search" role="form" method="post" action="/school/grades">
 		<div class="row">
  			<div class="col-md-11 form-group">
  				<div class="form-inline">
  					<label class="control-label"><strong>학생명 :</strong></label>
  					<input type="text" class="form-control" name="name" placeholder="이름입력" value="${searchParam}">
  				</div>
  			</div>
  			<div class="col-md-1">
  				<button id="button_search" type="button" class="btn btn-default editable" ><span class="glyphicon glyphicon-search"></span>  검색</button>
  			</div>
 		</div>
 	</form>
	
	<span style="color:green;font-weight:bold">전체 평균 : <fmt:formatNumber value="${gradeAvg}" pattern=".00"/></span>
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="10%">
			<col width="30%">
			<col width="30%">
			<col width="30%">
			<col width="10%">
		</colgroup>
		<thead>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>과목</th>
			<th>점수</th>
			<th>삭제</th>
		</tr>
		</thead>
  		<tbody>
		<c:forEach var="grade" items="${gradeList}">
			<tr>
				<td>${grade.id}</td>
				<td>${grade.schoolUser.name}</td>
				<td>${grade.subject.subjectName}</td>
				<td>${grade.score}</td>
				<td><button type="button" class="btn btn-danger editable" >삭제</button></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
//검색버튼
jQuery("#button_search").click(function(event){
	event.preventDefault();
	
	jQuery("#form_search").submit();
});

//삭제버튼
jQuery(".btn-danger").click(function(event){
	event.preventDefault();
	
	if(confirm("삭제 하시겠습니까?")) {
		var id = jQuery(this).parents("td").siblings().eq(0).html();
    	
    	$.post( "/school/grades/delete/" + id, function( data ) {
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