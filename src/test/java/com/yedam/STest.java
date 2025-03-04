package com.yedam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yedam.common.DataSource;
import com.yedam.mapper.ReplyMapper;

public class STest {
	public static void main(String[] args) {
//		Feb 28, 2025, 2:16:32 PM
//		2025. 2. 28. 오후 2:18:10
//		28 Feb 2025 05:18:38 GMT

//		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a");
//		System.out.println(sdf.format(new Date()));

		SqlSessionFactory sqlSessionFactory = DataSource.getInstance();
		SqlSession sqlSession = sqlSessionFactory.openSession();

		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);

		Map<String, String> map = new HashMap<>();
		map.put("title", "연구발표");
		map.put("start", "2025-02-09");
		map.put("end", "2025-02-11");

		mapper.insertEvent("연구발표2", "2025-02-11", "2025-02-12");
		sqlSession.commit(true);

		List<Map<String, Object>> list = mapper.fullData();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(list));

	}
}
