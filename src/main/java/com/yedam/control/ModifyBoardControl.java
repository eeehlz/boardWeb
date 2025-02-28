package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.dao.BoardDAO;
import com.yedam.vo.BoardVO;

public class ModifyBoardControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ?bno=22&title=test&content=testing
		String bno = req.getParameter("bno");
		String title = req.getParameter("title");
		String content = req.getParameter("content");

		BoardVO board = new BoardVO(); // 메소드의 파라미터.
		board.setBoardNo(Integer.parseInt(bno));
		board.setTitle(title);
		board.setContent(content);

		BoardDAO bdao = new BoardDAO();
		if (bdao.updateBoard(board)) { // 목록이동.
			resp.sendRedirect("boardList.do");
		} else {
			System.out.println("처리실패.");
		}

	}

}
