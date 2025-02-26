package com.yedam.serv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.dao.StudentDAO;
import com.yedam.vo.Student;

// service()기능구현.
@WebServlet("/addStudentServ")
public class AddStudentServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); // 요청정보의 한글처리.
		resp.setContentType("text/plain;charset=utf-8");
		// param 의 값을 활용 -> db 입력.
		// 처리성공 / 처리실패 메세지.
		String sno = req.getParameter("std_no");
		String sname = req.getParameter("std_name");
		String tel = req.getParameter("tel_no");
		String addr = req.getParameter("std_addr");

		// 매개값으로 Student객체.
		Student std = new Student();
		std.setStudentNo(sno);
		std.setStudentName(sname);
		std.setPhone(tel);
		std.setAddress(addr);

		StudentDAO sdao = new StudentDAO();
		if (sdao.addStudent(std)) {
			resp.getWriter().print("처리성공");
		} else {
			resp.getWriter().print("처리실패");
		}

	}
}
