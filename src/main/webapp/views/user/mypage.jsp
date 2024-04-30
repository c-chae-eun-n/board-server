<%@page import="boardServer.user.UserResponseDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<jsp:include page="/header"></jsp:include>
<body>
	<section id="root">
		<%
		if(session.getAttribute("user") == null)
			response.sendRedirect("/login");
		
		UserResponseDto user = (UserResponseDto) session.getAttribute("user");
		String userName = user.getName();
		%>
		<h1><%=userName %>님 환영합니다</h1>
		<p><%=user.getTelecom() %></p>
	
		<!-- 두 개가 같은 코드 -->
	
		<!-- <h1>${user.name}님 환영합니다!</h1> -->
		
		<button onclick="location.href='/updateUser'">회원정보 수정</button>
		<button onclick="location.href='/deleteUserPro'">회원 탈퇴</button>
		
	</section>
</body>
<jsp:include page="/footer"></jsp:include>
</html>