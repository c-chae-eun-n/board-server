package boardServer.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardServer.user.model.UserDao;
import boardServer.user.model.UserResponseDto;

/**
 * Servlet implementation class LoginFormAction
 */
public class LoginFormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginFormAction() {
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
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		boolean isValid = true;
		
		if(id == null || id.equals(""))
			isValid = false;
		else if(password == null || password.equals(""))
			isValid = false;
		
		if(isValid){
			// 연동된 데이터 베이스로부터	 (UserDao)
			// 유저의 정보를 조회 하고,	 (findUserByIdAndPassword())
			// 정보가 일치하면		 	 (return된 UserResponseDto가 null이 아니면 )
			// 로그인 처리 후, 페이지 이동	 (jsp 내장객체 중 session에 유저정보 저장) -> 브라우저가 닫히기 전까지 유지 즉, 로그인을 한번 하면 다른페이지로 넘어가도 다시 로그인을 하지 않을 수 있도록
			
			UserDao userDao = UserDao.getInstance();
			UserResponseDto user = userDao.findUserByIdAndPassword(id, password);
			
			// session 사용하기 위해
			HttpSession session = request.getSession();
			
			if(user != null) {
				session.setAttribute("user", user);
				response.sendRedirect("/mypage");
			} else {
				response.sendRedirect("/login");
			}
			
		} else 
			response.sendRedirect("/login");
	}

}
