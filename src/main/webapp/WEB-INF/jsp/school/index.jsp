<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>성적관리시스템</title>
</head>
<body>
<h1>성적관리시스템</h1>

<pre style="font-size: 17px">
요구사항 :
학교의 인원과 성적을 관리하는 시스템을 개발한다. 

1. 인원
학교의 인원은 교직원, 선생님, 학생으로 구성되며, 
학생은 id, 이름, 생년월일을 관리한다. 
교직원은 id, 이름, 생년월일을 관리한다. 
선생님은 id, 이름, 과목, 생년월일을 관리한다.  

2. 성적
학생의 성적은 과목별로 관리된다. 
과목은 id, 과목명이 관리되며, 성적은 양수(100점 만점)로 저장된다.  

3. 기능 : 
인원관리 - 학생, 교직원, 선생님을 입력/수정/삭제/조회한다. (각 최대 1000명)
과목관리 - 과목을 입력/수정/삭제/조회한다. (최대 100과목)
성적관리 - 학생의 과목별성적을 입력/수정/조회(전체 조회, 학생별 조회, 학생 평균, 전체 평균 등)/삭제한다. 

==============================================================================================

환경 : java 1.8
       embedded hsqldb
       embedded tomcat
       spring boot
       
제약조건 : '인원'의 이름 + ROLE에 유니크 제약조건
           과목명에 대해 유니크 제약조건
           '과목' 혹은 '인원' 삭제 시에 외래키로 인하여 '성적' 자동 삭제
          
url : 1) readme - http://localhost:8080/school
      2) 인원리스트 - http://localhost:8080/school/users
                   - 인원 등록, 수정, 삭제
      3) 과목리스트 - http://localhost:8080/school/subjects
                   - 과목 등록, 수정, 삭제, 과목별 성적 일괄 입력(학생 이름이 존재하지 않으면 무시됨)
      4) 성적리스트 - http://localhost:8080/school/grades
                   - 성적 전체 및 전체평균 조회, 학생별 조회 가능, 성적 단건 삭제

</pre>

</body>
</html>