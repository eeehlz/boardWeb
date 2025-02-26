package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yedam.dao.BoardDAO;
import com.yedam.vo.BoardVO;

public class AddBoardControl implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
				String saveDir = req.getServletContext().getRealPath("images");
			MultipartRequest mr = new MultipartRequest(
					req // 1. 요청객체
				   ,saveDir // 2. 파일저장경로
				   ,1024*1024*5//3. 최대 파일 크기
				   ,"utf-8" // 4. 인코딩
				   ,new DefaultFileRenamePolicy() // 5. 리네임정책
					);
			
				
		// 3개 파라미터 활용. db저장. 목록으로 이동.
		
//		String title = req.getParameter("title");
//		String content = req.getParameter("content");
//		String writer = req.getParameter("writer");
		
		
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String writer = mr.getParameter("writer");
		String img = mr.getFilesystemName("img");
		
		//매개값으로 활용.
		BoardVO bvo = new BoardVO();
		bvo.setTitle(title);
		bvo.setContent(content);
		bvo.setWriter(writer);
		bvo.setImg(img); // 추가 img컬럼.
		
		
		BoardDAO bdao = new BoardDAO();
		if (bdao.insertBoard(bvo)) {
			try {
				resp.sendRedirect("boardList.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("실패");
		}

	}

}
