package com.yedam.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.yedam.vo.ReplyVO;

public interface ReplyMapper {
	public ReplyVO selecReply(int replyNo);
	public int insertReply(ReplyVO reply);
	public int deleteReply(int replyNo);
	public List<ReplyVO> replyList(@Param("boardNo") int boardNo, @Param("page") int page);
	public int replyCount(int boardNo);
	public List<ReplyVO> replyListAll(int board);
	
	public List<Map<String, Object>> fullData();
	public int insertEvent(@Param("title") String title //
			, @Param("start") String start //
			, @Param("end") String end);
}
