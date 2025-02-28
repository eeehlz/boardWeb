package com.yedam.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.common.SearchVO;
import com.yedam.vo.BoardVO;

/*
 * 추가,수정,삭제,조회
 * Create,Read,Update,Delete
 */
public class BoardDAO extends DAO {

	// 페이징의 처리를 위한 실체데이터.
	public int getTotalCount(SearchVO search) {
		String sql = "select count(1) from tbl_board";
		if (search.getSearchCondition().equals("T")) {
			sql += "           where title like '%'||?||'%' ";
		} else if (search.getSearchCondition().equals("W")) {
			sql += "           where writer like '%'||?||'%' ";
		} else if (search.getSearchCondition().equals("TW")) {
			sql += "           where title like '%'||?||'%' or writer like '%'||?||'%' ";
		}
		try {
			psmt = getConnect().prepareStatement(sql);
			int cnt = 1;
			if (search.getSearchCondition().equals("T")) { // 제목검색.
				psmt.setString(cnt++, search.getKeyword());
			} else if (search.getSearchCondition().equals("W")) { // 작성자검색.
				psmt.setString(cnt++, search.getKeyword());
			} else if (search.getSearchCondition().equals("TW")) { // 제목, 작성자 검색.
				psmt.setString(cnt++, search.getKeyword());
				psmt.setString(cnt++, search.getKeyword());
			}
			rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1); // count(1) 값.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return 0; // 조회된 정보 없음.
	}

	// 글조회수 증가.
	public void updateCount(int boardNo) {
		String sql = "update tbl_board" //
				+ "   set    view_cnt = view_cnt + 1" //
				+ "   where board_no = ?";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setInt(1, boardNo);
			psmt.executeUpdate(); // 쿼리실행.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
	}

	// 상세조회. 글번호 => 전체정보 반환.
	public BoardVO getBoard(int boardNo) {
		String sql = "select board_no" //
				+ "         ,title" //
				+ "         ,content" //
				+ "         ,writer" //
				+ "         ,write_date" //
				+ "         ,view_cnt" //
				+ "         ,img" //
				+ "   from tbl_board" //
				+ "   where board_no = ?";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setInt(1, boardNo); //
			rs = psmt.executeQuery();
			// 조회결과 존재하면...
			if (rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoardNo(rs.getInt("board_no"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWriter(rs.getString("writer"));
				board.setWriteDate(rs.getDate("write_date"));
				board.setViewCnt(rs.getInt("view_cnt"));
				board.setImg(rs.getString("img"));
				// 결과반환.
				return board;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return null; // 조회결과 없음.
	} // end of getBoard.

	// 조회()
	public List<BoardVO> selectBoard(SearchVO search) {
		List<BoardVO> boardList = new ArrayList<>();
		String qry = "select tbl_b.* " //
				+ "from (select rownum rn, tbl_a.* " //
				+ "      from (select board_no, title, content, writer, write_date, view_cnt " //
				+ "            from tbl_board "; //
		if (search.getSearchCondition().equals("T")) {
			qry += "           where title like '%'||?||'%' ";
		} else if (search.getSearchCondition().equals("W")) {
			qry += "           where writer like '%'||?||'%' ";
		} else if (search.getSearchCondition().equals("TW")) {
			qry += "           where title like '%'||?||'%' or writer like '%'||?||'%' ";
		}
		qry += "            order by board_no desc) tbl_a) tbl_b " //
				+ "where tbl_b.rn >= (? - 1 )* 5 + 1 " //
				+ "and   tbl_b.rn <= ? * 5";
		try {
			psmt = getConnect().prepareStatement(qry);
			// 조건.
			int cnt = 1;
			if (search.getSearchCondition().equals("T")) { // 제목검색.
				psmt.setString(cnt++, search.getKeyword());
			} else if (search.getSearchCondition().equals("W")) { // 작성자검색.
				psmt.setString(cnt++, search.getKeyword());
			} else if (search.getSearchCondition().equals("TW")) { // 제목, 작성자 검색.
				psmt.setString(cnt++, search.getKeyword());
				psmt.setString(cnt++, search.getKeyword());
			}
			psmt.setInt(cnt++, search.getPage()); // 페이지.
			psmt.setInt(cnt++, search.getPage()); // 페이지.

			rs = psmt.executeQuery();
			// 조회결과.
			while (rs.next()) {
				BoardVO brd = new BoardVO();
				brd.setBoardNo(rs.getInt("board_no"));
				brd.setContent(rs.getString("content"));
				brd.setTitle(rs.getString("title"));
				brd.setViewCnt(rs.getInt("view_cnt"));
				brd.setWriteDate(rs.getDate("write_date"));
				brd.setWriter(rs.getString("writer"));

				boardList.add(brd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return boardList;
	}

	// 추가
	public boolean insertBoard(BoardVO board) {
		String sql = "insert into tbl_board (board_no, title, content, writer, img) " //
				+ "   values(board_seq.nextval,?,?,?,?)";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getContent());
			psmt.setString(3, board.getWriter());
			psmt.setString(4, board.getImg());

			int r = psmt.executeUpdate(); // insert 실행.
			if (r == 1) {
				return true; // 정상 등록.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return false; // 비정상 처리.
	}

	// 수정
	public boolean updateBoard(BoardVO board) {
		String sql = "update tbl_board " //
				+ "set    title = ? " //
				+ "      ,content = ? " //
				+ "where board_no = ?";
		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getContent());
			psmt.setInt(3, board.getBoardNo());
			int r = psmt.executeUpdate(); // 쿼리실행.
			if (r > 0)
				return true; // 정상수정.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return false;
	}

	// 삭제()
	public boolean deleteBoard(int boardNo) {
		String query = "delete from tbl_board where board_no = ?";
		try {
			psmt = getConnect().prepareStatement(query);
			psmt.setInt(1, boardNo); // ?에 값 지정.
			int r = psmt.executeUpdate(); // 쿼리 실행.
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect(); // 정상실행이거나 예외발생이나 반드시 실행할 코드.
		}
		return false;
	} // end of deleteBoard.
}
