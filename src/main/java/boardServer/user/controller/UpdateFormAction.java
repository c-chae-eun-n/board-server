package boardServer.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardServer.user.model.UserDao;
import boardServer.user.model.UserRequestDto;
import boardServer.user.model.UserResponseDto;

/**
 * Servlet implementation class UpdateFormAction
 */
public class UpdateFormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFormAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();

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
			
			if(!telecom.equals(user.getTelecom()) || !phone.equals(user.getPhone())) {
				userDto.setTelecom(telecom);
				userDto.setPhone(phone);
				user = userDao.updateUserPhone(userDto);
			}
		}
		
		// 갱신 된 내용을 세션에 반영
		session.setAttribute("user", user);
		response.sendRedirect("/mypage");
	}

}
