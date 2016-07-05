<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>맛집 리스트</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h3>맛집 리스트</h3>
	
	<form id="form_search" role="form" method="get" action="/dining">
 		<div class="row">
  			<div class="col-md-11 form-group">
  				<div class="form-inline">
  					<label class="control-label"><strong>맛집명 :</strong></label>
  					<input type="text" class="form-control" name="name" placeholder="이름입력" value="${searchParam}">
  				</div>
  			</div>
  			<div class="col-md-1">
  				<button id="button_search" type="button" class="btn btn-default editable" ><span class="glyphicon glyphicon-search"></span>  검색</button>
  			</div>
 		</div>
 	</form>
	
	<table class="table table-bordered table-striped">
		<colgroup>
			<col width="5%">
			<col width="20%">
			<col width="20%">
			<col width="35%">
			<col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>태그</th>
				<th>주소</th>
				<th>전화번호</th>
			</tr>
		</thead>
  		<tbody>
		<c:forEach var="restaurant" items="${restaurantList}">
			<tr>
				<td>${restaurant.id}</td>
				<td><a href="/dining/${restaurant.id}">${restaurant.name}</a></td>
				<td>${restaurant.tag}</td>
				<td>${restaurant.address}</td>
				<td>${restaurant.tel}</td>
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
</script>
</body>
</html>