package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yedam.dao.MemberDAO;
import com.yedam.vo.MemberVO;

public class LoginControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청방식(get/ post)
		if(req.getMethod().equals("GET")) {
			// 1.로그인 화면.
			req.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(req, resp);
		
		} else if(req.getMethod().equals("POST")) {
		// 2.로그인 기능.
			String id = req.getParameter("uname");
		    String pw = req.getParameter("psw");
		    
		    //로그인 체크
		    MemberDAO mdao = new MemberDAO();
		    MemberVO mvo = mdao.login( id, pw);
		    
		    if(mvo != null) {
		    	System.out.println("환영합니다. " + mvo.getMemberName());
		    	HttpSession session = req.getSession();
		    	session.setAttribute("logId", id); // attribute활용.
		    	// 일반사용자 or 관리자 권한
		    	if(mvo.getResponsibility().equals("Admin")) {
		    		resp.sendRedirect("memberList.do");
		    	} else {
		    		resp.sendRedirect("boardList.do");
		    	}
		    	
		    }else {
		    	System.out.println("id, pw 확인");
		    }

		}
	}

}
