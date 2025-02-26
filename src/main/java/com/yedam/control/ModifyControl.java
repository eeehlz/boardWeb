package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yedam.dao.BoardDAO;
import com.yedam.vo.BoardVO;

public class ModifyControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정화면
		String bno = req.getParameter("bno");

		BoardDAO bdao = new BoardDAO();
		BoardVO board = bdao.getBoard(Integer.parseInt(bno)); 
		
		//세션 아이디  글작성 아이디
		HttpSession session = req.getSession();
		String sessionId = (String) session.getAttribute("logId");
	    String writerId = board.getWriter();
	    
	    if(session == null) {
	    	req.setAttribute("msg", "로그인하세요.");
	    	return;
	    }
	    
	    if (!sessionId.equals(writerId)) {
	    	req.setAttribute("msg", "권한을 확인하세요.");
	    	req.setAttribute("board", board);
	    	req.getRequestDispatcher("/WEB-INF/views/board.jsp").forward(req, resp);
	    	return;
	    }

		// 요청정보의 attribute활용.
		req.setAttribute("board", board);
		req.getRequestDispatcher("board/modifyBoard.tiles").forward(req, resp);

	}

}
