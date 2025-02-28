package com.yedam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.InsertDataControl;
import com.yedam.control.AddBoardControl;
import com.yedam.control.AddFormControl;
import com.yedam.control.AddMemberControl;
import com.yedam.control.AddReplyControl;
import com.yedam.control.AjaxControl;
import com.yedam.control.BoardControl;
import com.yedam.control.BoardListControl;
import com.yedam.control.CalendarControl;
import com.yedam.control.ChartControl;
import com.yedam.control.ChartData;
import com.yedam.control.Control;
import com.yedam.control.DataControl;
import com.yedam.control.DataTableControl;
import com.yedam.control.FullDataControl;
import com.yedam.control.LoginControl;
import com.yedam.control.LogoutControl;
import com.yedam.control.MainControl;
import com.yedam.control.MemberListControl;
import com.yedam.control.ModifyBoardControl;
import com.yedam.control.ModifyControl;
import com.yedam.control.RemoveBoardControl;
import com.yedam.control.RemoveMemberControl;
import com.yedam.control.RemoveReplyControl;
import com.yedam.control.ReplyCount;
import com.yedam.control.ReplyListControl;

/*
 * MVC에서 Control역할.
 * url요청 -> 서블릿.
 */
//@WebServlet("*.do")
public class FrontController extends HttpServlet {
	Map<String, Control> map;

	public FrontController() {
		map = new HashMap<>(); // map 필드의 초기화.
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		map.put("/main.do", new MainControl());
//		map.put("url", "servlet"); // addStudent.do AddStudentServlet
		map.put("/boardList.do", new BoardListControl()); // 글목록.
		map.put("/addForm.do", new AddFormControl()); // 등록화면.
		map.put("/addBoard.do", new AddBoardControl()); // 등록처리.
		map.put("/board.do", new BoardControl()); // 상세화면.
		map.put("/modifyForm.do", new ModifyControl()); // 수정화면.
		map.put("/modifyBoard.do", new ModifyBoardControl()); // 수정처리.
		// 삭제화면, 삭제처리.
		map.put("/removeBoard.do", new RemoveBoardControl());

		// 로그인.
		map.put("/loginForm.do", new LoginControl()); // 화면.
		map.put("/login.do", new LoginControl()); // 로그인처리.
		map.put("/logout.do", new LogoutControl()); // 로그아웃.
		
		map.put("/memberList.do", new MemberListControl()); //회원목록
		map.put("/testAjax.do", new AjaxControl());
		map.put("/testData.do", new DataControl());
		//회원삭제
		map.put("/removeMember.do", new RemoveMemberControl());
		//회원등록
		map.put("/addMember.do", new AddMemberControl());
		//댓글관련.
		map.put("/replyList.do", new ReplyListControl());//목록
		map.put("/addReply.do", new AddReplyControl());//등록
		map.put("/removeReply.do", new RemoveReplyControl());//삭제
		map.put("/getReplyCnt.do", new ReplyCount());
		//차트
		map.put("/chart.do", new ChartControl());
		map.put("/chartData.do", new ChartData());		
		//datatable 권한.
		map.put("/datatable.do", new DataTableControl());
		//캘린더
		map.put("/full.do", new CalendarControl());
		map.put("/fullData.do", new FullDataControl()); //조회
		map.put("/insertData.do", new InsertDataControl()); //등록
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("front control");
		// http://localhost:8080/BoardWeb/addStudent.do => url
		// /BoardWeb/addStudent.do => uri
		String uri = req.getRequestURI(); // "/BoardWeb/addStudent.do"
		String context = req.getContextPath(); // "/BoardWeb"
		String page = uri.substring(context.length());

		System.out.println(page);
		// map컬렉션에서 key를 입력하면 val반환.
		Control control = map.get(page);
		control.exec(req, resp);
	}
}
