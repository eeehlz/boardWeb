package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.dao.MemberDAO;

public class RemoveMemberControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = req.getParameter("mid");
		
		//MemberDAO에 삭제. boolean // method=> deleteMember(String id);
		MemberDAO mdao = new MemberDAO();
		// 정상삭제 : true, 처리예외: false;
		boolean isOk = mdao.deleteMember(id);
		
		if(isOk) {
			// {"retCode": "OK"} << JSON코드 반영
			resp.getWriter().print("{\"retCode\": \"OK\"}");
		}else {
			// {"retCode": "NG"}
			resp.getWriter().print("{\"retCode\": \"NG\"}");
		}
	}

}
