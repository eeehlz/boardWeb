package com.yedam.dao;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.vo.MemberVO;


public class MemberDAO extends DAO {

	public List<MemberVO> members() {
		String sql = "select member_id"//
				+ "         ,passwd"//
				+ "         ,member_name"//
				+ "         ,responsibility"//
				+ "   from tbl_member ";
		List<MemberVO> list = new ArrayList<>();
		// 조회.
		try {
			psmt = getConnect().prepareStatement(sql);
			rs = psmt.executeQuery(); // 쿼리실행.

			while (rs.next()) { // 조회된 결과가 있으면.
				MemberVO mvo = new MemberVO();
				mvo.setMemberId(rs.getString("member_id"));
				mvo.setPasswd(rs.getString("passwd"));
				mvo.setMemberName(rs.getString("member_name"));
				mvo.setResponsibility(rs.getString("responsibility"));
				
				list.add(mvo); // 건수만큼 list추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect();
		}
	
		return list;// 조회결과 없음.
	}
		
		
	
		public MemberVO login(String id, String pw) {
			String sql = "select member_id"//
					+ "         ,passwd"//
					+ "         ,member_name"//
					+ "         ,responsibility"//
					+ "   from tbl_member "//
					+ "   where member_id = ?"//
					+ "   and   passwd = ?";
			// 조회.
			try {
				psmt = getConnect().prepareStatement(sql);
				psmt.setString(1, id);
				psmt.setString(2, pw);

				rs = psmt.executeQuery(); // 쿼리실행.

				if (rs.next()) { // 조회된 결과가 있으면.
					MemberVO mvo = new MemberVO();
					mvo.setMemberId(rs.getString("member_id"));
					mvo.setPasswd(rs.getString("passwd"));
					mvo.setMemberName(rs.getString("member_name"));
					mvo.setResponsibility(rs.getString("responsibility"));
					return mvo;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disConnect();
			}
			return null; // 조회결과 없음.
		
	}


			//삭제
		public boolean deleteMember(String id) {
			String sql = "delete from tbl_member where member_id = ?";
			try {
				psmt=getConnect().prepareStatement(sql);
				psmt.setString(1, id);
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
		
		//추가
		public boolean insertMember(MemberVO member) {
			String sql = "insert into tbl_member (member_id, passwd, member_name)"
					+" values(?, ?, ?)";
			try {
				psmt=getConnect().prepareStatement(sql);
				psmt.setString(1, member.getMemberId());
				psmt.setString(2, member.getPasswd());
				psmt.setString(3, member.getMemberName());
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
}
