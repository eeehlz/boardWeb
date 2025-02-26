package com.yedam.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.vo.Student;

public class StudentDAO extends DAO {

	public boolean addStudent(Student student) {
		String sql = "insert into tbl_student (student_no, student_name, phone, address) " //
				+ "values(?, ?, ?, ?)";

		try {
			psmt = getConnect().prepareStatement(sql);
			psmt.setString(1, student.getStudentNo()); // 첫번째 ?에 값을 할당.
			psmt.setString(2, student.getStudentName());
			psmt.setString(3, student.getPhone());
			psmt.setString(4, student.getAddress());

			// 쿼리실행.
			int r = psmt.executeUpdate(); // 처리된 건수반환.
			if (r > 0) {
				return true;// 등록성공.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // 등록실패.
	}

	// 학생목록을 반환 메소드. 참고) EmpDAO.search()
	public List<Student> studentList() {
		List<Student> studentList = new ArrayList<>();
		String qry = "select * from tbl_student " //
				+ "order by student_no";
		try {
//			Statement stmt = getConnect().createStatement();
			psmt = getConnect().prepareStatement(qry);
			rs = psmt.executeQuery();
			// 조회결과.
			while (rs.next()) {
				Student student = new Student();
				student.setStudentNo(rs.getString("student_no"));
				student.setStudentName(rs.getString("student_name"));
				student.setPhone(rs.getString("phone"));
				student.setAddress(rs.getString("address"));

				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;
	} // end of studentList()
}
