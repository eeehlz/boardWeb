package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.yedam.common.DataSource;
import com.yedam.mapper.ReplyMapper;

public class ReplyCount implements Control {

	@Override
	public void exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		String bno = req.getParameter("bno");
		
		//ReplyDAO rdao = new ReplyDAO();
		SqlSession sqlSession = DataSource.getInstance().openSession();
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);
		int totalCnt = mapper.replyCount(Integer.parseInt(bno));		
		//json반환{"totalCnt": 30}
		resp.getWriter().print("{\"totalCnt\": "+ totalCnt +"}");
	}

}
