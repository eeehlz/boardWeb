<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h3>Ajax 연습페이지.</h3>
<!-- 등록화면 -->
<table class="table">
	<tr>
		<th>회원ID</th>
		<td><input type="text" name="mid"></td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td><input type="password" name="mpw"></td>
	</tr>
	<tr>
		<th>회원이름</th>
		<td><input type="text" name="mname"></td>
	</tr>
	<tr>
		<td colspan="2" align="center" class="btn btn-primary">
			<button id="addMember">추가</button>
		</td>
	</tr>
</table>


<h3>회원목록</h3>
<table class="table">
	<thead>
		<tr>
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			<th>권한</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody id="list">
		<!--   <tr><td>user01</td><td>1111</td><td>홍길동</td><td>user</td></tr>-->
	</tbody>

</table>

<script src="js/member.js">
	
</script>

