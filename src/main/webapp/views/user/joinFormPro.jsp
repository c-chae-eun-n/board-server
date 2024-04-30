<%@page import="boardServer.user.UserResponseDto"%>
<%@page import="boardServer.user.UserDao"%>
<%@page import="boardServer.user.UserRequestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<%
	// 파라미터로 전달 받은 값 가져오기
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String password = request.getParameter("password");
	String email = request.getParameter("email");
	String name = request.getParameter("name");
	String birth = request.getParameter("birth");
	String gender = request.getParameter("gender");
	String country = request.getParameter("country");
	String telecom = request.getParameter("telecom");
	String phone = request.getParameter("phone");
	String agree = request.getParameter("agree");
	// boolean agree = Boolean.parseBoolean(request.getParameter("agree"));		// String -> boolean 형변환
	// ㄴ 값 자체가 널이면 오류가 발생
	
	// Backend 에서 전달받은 데이터에 대한 유효성 검증
	
	boolean isValid = true;
	
	if(id == null || id.equals(""))
		isValid = false;
	else if(password == null || password.equals(""))
		isValid = false;
	else if(name == null || name.equals(""))
		isValid = false;
	else if(birth == null || birth.equals(""))
		isValid = false;
	else if(gender == null || gender.equals(""))
		isValid = false;
	else if(country == null || country.equals(""))
		isValid = false;
	else if(telecom == null || telecom.equals(""))
		isValid = false;
	else if(phone == null || phone.equals(""))
		isValid = false;
	else if(agree == null)
		isValid = false;
	
	// Processing Page 에서는 사용자에게 보여주는 화면을 작성하지 않음
	// 요청에 대한 응답 처리를 작성
	// 1) 페이지 이동 처리 ( 흐름 제어 )
	if(isValid) {
		// 연동된 데이터 베이스로부터
		// 유저의 정보를 조회 하고,
		// 중복에 대한 검증을 한 후에
		// 가입 처리 후, 페이지 이동
		
		// 용어 정리
		// DAO (Data Access Object) : 데이터베이스에 접근하도록 돕는 역할
		// VO (Value Object) : 조회한 튜플(을 오브젝트 형태로 가지는) 객체 (순수 조회 용도라서 getter만 제공)
		// DTO (Data Transfer Object) : 서비스용 데이터 객체 (setter도 제공)
		//  ㄴ  RequestDto : 사용자단에서 넘어온 데이터들을 객체로 만든 것(신뢰하지 않음)
		//  ㄴ  ResponseDto : VO 객체로부터 선별된 데이터에 대한 -> Clone한 결과물 (실객체가 돌아다니지 않도록 다리를 하나 더 둔 것)
		
		UserRequestDto userDto = new UserRequestDto(id, password, email, name, birth, gender, country, telecom, phone, agree);
		
		UserDao userDao = UserDao.getInstance();
		UserResponseDto user = userDao.createUser(userDto);
		
		if(user == null) {
			// 실패
			response.sendRedirect("/join");
		} else {
			// 성공
			System.out.println("user : " + user);
			
			response.sendRedirect("/login");
		}
		
	}
		

%>
</body>
</html>