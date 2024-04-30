<%@page import="boardServer.user.UserRequestDto"%>
<%@page import="boardServer.user.UserResponseDto"%>
<%@page import="boardServer.user.UserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");

	UserDao userDao = UserDao.getInstance();
	
	// session에 저장되어있는 타입
	UserResponseDto user = (UserResponseDto) session.getAttribute("user");

	String password = request.getParameter("password");
	
	// 입력된 패스워드 검증 후
	if(userDao.findUserByIdAndPassword(user.getId(), password) != null) {
		UserRequestDto userDto = new UserRequestDto();
		
		userDto.setId(user.getId());
		userDto.setPassword(password);
		
		String newPassword = request.getParameter("password-new");
		String email = request.getParameter("email");
		
		String telecom = request.getParameter("telecom");
		String phone = request.getParameter("phone");
		
		// if-else 아니므로 전부 다 변경시 모두 호출 됨
		if(!newPassword.equals("") && !newPassword.equals(password)) {
			userDao.updateUserPassword(userDto, newPassword);
		}
		
		if(user.getEmail() != null && !email.equals(user.getEmail())) {
			userDto.setEmail(email);
			
			// 변경된 내역을 user에 담아줌
			user = userDao.updateUserEmail(userDto);
		}
		
		out.print("telecom : "+user.getPhone());
		out.print("telecom : "+user.getTelecom());
		
		if(!telecom.equals(user.getTelecom()) || !phone.equals(user.getPhone())) {
			userDto.setTelecom(telecom);
			userDto.setPhone(phone);
			user = userDao.updateUserPhone(userDto);
		}
	}
	
	// 갱신 된 내용을 세션에 반영
	session.setAttribute("user", user);
	response.sendRedirect("/mypage");
	
%>
</body>
</html>