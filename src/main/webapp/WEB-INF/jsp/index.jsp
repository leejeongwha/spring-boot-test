<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항</title>
</head>
<body>
<pre>
Spring Framework를 사용하여 관리자만 게시 가능한 공지사항 구현하기
1. 신규 프로젝트 생성
	A. File > New > Spring Starter Project를 선택하여 New Spring Starter Project 팝업 띄우기
	B. Project name 을 sample_notice로 설정하고 dependency는 JPA, HSQLDB, Actuator, WEB 선택
	C. pom.xml 에 org.apache.tomcat.embed.tomcat-embed-jasper, javax.servlet.jstl 추가
	D. 메인클래스 SpringBootServletInitializer 상속받도록 설정
	E. application.properties에 spring.mvc.view.prefix: /WEB-INF/jsp/, spring.mvc.view.suffix: .jsp 추가
	
2. HSQL DB사용 및 hibernate 사용을 위한 설정 추가
	A. src/main/resource 하위에 schema.sql, data.sql 생성
	B. WebMvcConfigurerAdapter를 상속받은 WebMvcConfig.java에 인터셉터 추가
	C. PersistenceConfiguration.java 추가
	
3. 기능 요구사항
	A. 공지사항 리스트 보기(최신순으로) 및 제목 클릭 시 공지사항 상세 보기 
	B. 공지사항 상세에서 저장버튼 클릭시에 세션 참조하여 로그인 되어있지 않을 경우 로그인 폼으로 리다이렉트
	C. 세션에 어드민ID가 존재할 경우(로그인 되어있을 경우) 공지사항 저장 및 수정이 가능함
	D. 로그인이 성공하였을 경우 세션에 어드민 ID 저장 후 공지사항 리스트로 이동
	E. 로그인이 실패하였을 경우 세션초기화 후 다시 로그인페이지로 이동
	E. 공지사항 일부 보기 가능(get방식으로 page 및 count 설정 가능)
	F. 공지사항 일부 및 세부사항에 대해서 JSON 형태로 출력 가능
	G. AuthCheck 커스텀 어노테이션이 선언된 컨트롤러 메서드는 메서드가 수행되기 전 로그인여부 확인 후 
	로그인 되어있으면 메서드 수행, 미 로그인 시에는 로그인 페이지로 리다이렉트
	H. 화면에 출력되는 한글 메세지들은 전부 message_ko.properties 파일에 정의되어 있어야 합니다.
	(번호, 제목, 작성자 등등.. 화면에 직접적으로 메세지가 하드코딩되지 않고 i18n관련 기능 사용)
	I. 로그인 및 공지사항 등록은 POST방식만 허용해야 함(GET방식으로 등록 불가)
	
4. URL 리스트
	A. /notice/list?page=1&count=10 : 공지사항 리스트 보기(get방식으로 page 및 count 설정 가능)
	B. /notice/{seq} : 해당 sequence의 공지사항 상세 보기(수정 가능)
	C. /notice/form : 공지사항 신규등록
	D. /notice/save : 공지사항 제목 및 상세 저장
	E. /login/form : 로그인 폼
	F. /login/submit : 로그인 폼으로부터 넘어온 값 체크(일치하지 않을경우 세션 제거, 일치할 경우 세션 저장)
	G. /notice/json/list?page=1&count=10 : 공지사항 목록 json으로 보기(get방식으로 page 및 count 설정 가능)
	H. /notice/json/{seq} : 공지사항 단건 json으로 보기

5. jsp 페이지 리스트
	A. notice/detail.jsp : 공지사항 상세 및 저장버튼
	B. notice/list.jsp : 공지사항 목록 및 작성버튼
	C. user/loginForm.jsp : 로그인 폼
	
6. 추가 요구사항(선택사항)
	A. 리스트 화면에서 paging 지원을 위한 화면 부분 추가
	B. LocaleChangeInterceptor 사용하여 locale을 ko 및 en으로 설정하여 관련 message 프로퍼티 파일에서 메세지 읽어오도록 수정
	C. 오류 발생 시 현재는 에러코드가 그대로 화면에 노출되나 미리 설정된 ERROR 관련 페이지가 보여지도록 수정
	D. 현재 정해진 admin만 글쓰기가 가능한 상태이나 회원가입 받아서 내가 쓴글에 대해서 삽입, 수정, 삭제 가능하도록 수정(자유게시판처럼)
</pre>
</body>
</html>