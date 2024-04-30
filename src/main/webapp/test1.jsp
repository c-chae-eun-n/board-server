<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String pageData = (String) pageContext.getAttribute("test");
	String requestData = (String) request.getAttribute("test");
	String sessionData = (String) session.getAttribute("test");
	String applicationData = (String) application.getAttribute("test");

	out.print(String.format("pageData : <p>%s</b></p>", pageData));
	out.print(String.format("requestData : <p>%s</b></p>", requestData));
	out.print(String.format("sessionData : <p>%s</b></p>", sessionData));
	out.print(String.format("appData : <p>%s</p><br>", applicationData));
	%>

	<!-- JSP EL(Expression language) Tag -->
	<p>${pageScope.test}</p>
	<p>${requestScope.test}</p>
	<p>${sessionScope.test}</p>
	<p>${applicationScope.test}</p><br>
	<!-- jsp 페이지 안에서 <script></script>블럭을 노출하지 않도록 유의 그냥 쓰면 출동이 일어날 수 있음 -->

	<!-- Default 참조 영역 확인용 -->
	<p>${test}</p>

	<button onclick="location.href='test2.jsp'">Test2로 이동</button>

</body>
</html>