package com.yedam;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.yedam.common.DataSource;
import com.yedam.common.SearchVO;
import com.yedam.mapper.BoardMapper;

public class TTTestExe {
	public static void main(String[] args) {
		SqlSessionFactory sqlSessionFactory = DataSource.getInstance();
		SqlSession sqlSession = sqlSessionFactory.openSession();

		BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);

		SearchVO searchVO = new SearchVO(1, "T", "첫번");

	
		int row = mapper.getTotalCount(searchVO);
		System.out.println("건수: " + row);
	}	
}
