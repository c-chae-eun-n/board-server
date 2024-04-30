<%@page import="boardServer.user.UserResponseDto"%>
<%@page import="java.util.List"%>
<%@page import="boardServer.user.UserDao"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<jsp:include page="/header"></jsp:include>
<body>
	<section id="root">
		<%
		// JSP 내장 객체의 유효기간 (Scope)
			pageContext.setAttribute("test", "page data");
			request.setAttribute("test", "request data");
			session.setAttribute("test", "session data");
			application.setAttribute("test", "application data");
			
			// 페이지 이동처리 1)
			// ㄴ 단순 페이지 이동처리 (url의 변화가 O)
			// response.sendRedirect("test.jsp");
			
			// 페이지 이동처리 2)
			// ㄴ request & response 객체를 전달하면서 이동 처리 (url의 변화가 X)
			request.getRequestDispatcher("test1.jsp").forward(request, response);
		%>
	</section>
	
	<!--
	<퍼센트 
	// 간단한 DB 연동 확인 작업
	Connection conn = null;
	
	String url = "jdbc:mysql://localhost:3306/board_server_db";
	String user = "root";
	String password = "root";
	
	// 드라이버 로드
	Class.forName("com.mysql.cj.jdbc.Driver");
	
	conn = DriverManager.getConnection(url, user, password);
	
	if(conn != null){
		out.print("Database 연동 성공!!");
	}else {
		out.print("Database 연동 실패...");
	}
	퍼센트>
	-->
	
</body>
<jsp:include page="/footer"></jsp:include>
</html>
<!-- 
Server <-> Client
요청과 응담 (request & response)

JSP : Java Server Page
ㄴ html 문서 안에 java로 작성

JSP 페이지 작성 시
유지보수를 위한 규칙
1) 사용자에게 보여지는 화면			ex) form.jsp		[View]
2) 요청에 대한 처리로직을 담는 페이지		ex) formPro.jsp		[Control]
ㄴ 요청에 대한 결과에 따라 -> response를 -> 페이지 이동 처리로 중
ㄴ response.sendRedirect(URL)

Database 처리를 위해
VO (Value Object) : Read-only
DTO (Data Transfer Object) : 서비스 제출 간에 데이터를 전달하는 용도의 객체
ㄴ RequestDto, ResponseDto
DAO (Data Access Object) : Database에 접근할 수 있도록 돕는 객체
ㄴ Data 조작에 대한 인터페이스(method)를 제공하도록 설계 (CRUD 메소드)
ㄴ SQL > DML : SELECT(R) INSERT(C) UPDATE(U) DELETE(D)

ex) UserDao
ㄴ 구현한 DB에 연동 (JDBC)
ㄴ 인터페이스
  1) createUser()
  2) findUserBy()
  3) updateUser()
  4) deleteUser()
  
[데이터베이스]
database name: board_server_db
table1 : users
table2 : boards

JSP MVC 1
Model : 데이터베이스 등의 자바객체로 처리되는 단위들
ㄴ DAO, DTO, VO
View : 사용자에게 보여지는 화면 랜더링
ㄴ ~.jsp
Control : 사용자로부터 요청된 내용을 처리하는 로직
ㄴ ~pro.jsp => servlet 은 요청-응답을 수월하게 해주는 java로 작성된 program

CLI
Command Line Interface : 명령 프롬프트 인터페이스

GUI
Graphic User Interface
-->