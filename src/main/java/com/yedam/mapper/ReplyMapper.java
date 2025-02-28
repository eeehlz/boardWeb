package com.yedam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.vo.ReplyVO;

public interface ReplyMapper {

	public ReplyVO selecReply(int replyNo);
	public int insertReply(ReplyVO reply);
	public int deleteReply(int replyNo);
	public List<ReplyVO>replyList(@Param("boardNo") int boardNo, @Param("page") int page);
    public int replyCount(int boardNo);
	public List<ReplyVO> replyListAll(int board);
}
