package com.yedam.serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.dao.EmpDAO;
import com.yedam.vo.Employee;

@WebServlet("/empInfo")
public class GetEmployeeServ extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파라미터(?eno=1001)
		resp.setContentType("text/html;charset=utf-8");
		String eno = req.getParameter("eno");
		// 날짜포맷지정.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");

		EmpDAO edao = new EmpDAO();
		Employee result = edao.selectEmp(Integer.parseInt(eno));
		String str = "<table border='2'>"; // <table><tr><th>사번</th><td>1001</td></tr>....
		str += "<tr><th>사번</th><td>" + result.getEmpNo() + "</td><th>이름</th><td>" + result.getEmpName() + "</td></tr>";
		str += "<tr><th>전화</th><td>" + result.getTelNo() + "</td><th>급여</th><td>" + result.getSalary() + "</td></tr>";
		str += "<tr><th>입사일자</th><td colspan='3'>" + sdf.format(result.getHireDate()) + "</td></tr>";
		str += "</table>";
		// 목록(sample)이동하는 링크.
		str += "<a href='sample'>목록이동</a>";

		PrintWriter out = resp.getWriter(); // 출력스트림.
		out.print(str);

	}
}
