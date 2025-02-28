package com.yedam.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yedam.vo.ReplyVO;

//댓글목록, 등록, 삭제, 상세조회
public class ReplyDAO extends DAO {
	
	//부서별 인원현황  차트. 
	public List<Map<String, Object>> chartData(){
	//맵옆엔뭐에요? 리스트<맵<칼럼,값>>
		String sql = "select emp.department_id, dept.department_name, count(1) cnt "
					+" from employees emp "
					+" join departments dept "
					+" on   emp.department_id = dept.department_id "
					+" group by emp.department_id, dept.department_name";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			psmt = getConnect().prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				Map<String, Object>map = new HashMap<>();
				map.put("dept_name", rs.getString(2));
				map.put("dept_count", rs.getInt(3));
				list.add(map);
			}
		} catch (SQLException e) {
			// 
			e.printStackTrace();
		} finally {
			disConnect();
		}
		return list;
	}	

	
	//댓글의 건수 계산(페이징)
	public int replyCount(int boardNo) {
		String sql = "select count(1) from tbl_reply where board_no = ?";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setInt(1, boardNo);
			rs = psmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disConnect();
		}
		return 0;
	}

	//목록
	public List<ReplyVO>replyList(int boardNo, int page){
		String sql = "select tbl_a.* "
			+"	from (select /*+ INDEX_DESC (r pk_reply) */ "
			+"	          rownum rn, reply_no, reply, replyer, board_no, reply_date "
			+"	    from tbl_reply r "
			+"	    where board_no = ?) tbl_a "
			+"	where tbl_a.rn > (? - 1) * 5 "
			+"	and tbl_a.rn <= ? * 5";	
		List<ReplyVO> replyList = new ArrayList<>();
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setInt(1, boardNo); 
			psmt.setInt(2, page); 
			psmt.setInt(3, page); 
			rs = psmt.executeQuery();
			// 조회결과.
			while (rs.next()) {
				ReplyVO rep = new ReplyVO();
				rep.setReplyNo(rs.getInt("reply_no"));
				rep.setReply(rs.getString("reply"));
				rep.setReplyer(rs.getString("replyer"));
				rep.setReplyDate(rs.getDate("reply_date"));			
				rep.setBoardNo(rs.getInt("board_no"));
				replyList.add(rep);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return replyList;
	}
		
	
	
	//상세조회
	public ReplyVO selecReply(int replyNo) {
		String sql = "select * from tbl_reply " // 
				 + "where reply_no = ?";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setInt(1, replyNo);
			rs = psmt.executeQuery(); // 조회.
			if (rs.next()) { // 조회결과가 한건 있으면...
				ReplyVO rep = new ReplyVO();
				rep.setReplyNo(rs.getInt("reply_no")); // 칼럼값.
				rep.setReply(rs.getString("reply"));
				rep.setReplyer(rs.getString("replyer"));
				rep.setReplyDate(rs.getDate("reply_date"));
				return rep;// 반환.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // 조회된 결과 없음(null)
		
	}
	
	//등록
	public boolean insertReply(ReplyVO reply) {
		String query1="select reply_seq.nextval from dual";
		String sql = "insert into tbl_reply (reply_no, reply, replyer, board_no)"
				+" values(?, ?, ?, ?)";
		try {
			psmt=getConnect().prepareStatement(query1);
			rs=psmt.executeQuery();
			if(rs.next()) {
				reply.setReplyNo(rs.getInt(1));//첫번째 칼럼
			}
			
			
			psmt=getConnect().prepareStatement(sql);
			psmt.setInt(1, reply.getReplyNo());
			psmt.setString(2, reply.getReply());
			psmt.setString(3, reply.getReplyer());
			psmt.setInt(4, reply.getBoardNo());			
			int r = psmt.executeUpdate();
			if(r > 0) {
				return true; //정상등록
			}
		} catch (SQLException e) {				
			e.printStackTrace();
		}finally {
			disConnect(); //정상실행이거나 예외발생이거나 반드시 실행
		}
		return false; // 비정상처리 
	}
	
	//삭제
	public boolean deleteReply(int replyNo) {
		String sql = "delete from tbl_reply where reply_no = ?";
		try {
			psmt=getConnect().prepareStatement(sql);
			psmt.setInt(1, replyNo);
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {				
			e.printStackTrace();
		}finally {
			disConnect(); //정상실행이거나 예외발생이거나 반드시 실행
		}
		return false;
	}
	
	
	
}//end of class
