/**
 * member.js
 */
// 삭제 함수
// DB 삭제, 화면에서 지우기.
function deleteRow(id) {
	console.log(this);
	let btn = this;
	fetch("removeMember.do?mid=" + id) // 서버처리.
		.then(function(result) {
			return result.json();
		})
		.then((result) => {
			console.log(result);
			if (result.retCode == "OK") {
				document.querySelector('#tr_' + id).remove(); // 한건지우기.
			} else if (result.retCde == "NG") {
				alert('삭제오류 발생'); // 에러.
			} else {
				alert('알수없는 코드입니다.')
			}
		})
}// end of deleteRow()
fetch("testData.do")
	.then(function(result) {
		return result.json(); // stream -> object
	})
	.then(function(result) {
		const memberAry = result;
		memberAry.forEach(function(member) {
			console.log(member);
			const target = document.querySelector('#list');
			const html = `<tr id=tr_${member.memberId}>
							<td>${member.memberId}</td>
							<td>${member.passwd}</td>
							<td>${member.memberName}</td>
							<td>${member.responsibility}</td>
							<td><button onclick="deleteRow('${member.memberId}')" class="btn btn-danger">삭제</button></td>
						  </tr>`;
			target.insertAdjacentHTML('beforeend', html);
		});
	})

//추가 이벤트.
document.querySelector('#addMember').addEventListener('click', function(e) {
		const id = document.querySelector('input[name=mid]').value;
		const pw = document.querySelector('input[name=mpw]').value;
		const name = document.querySelector('input[name=mname]').value;
		fetch("addMember.do?mid=" + id + "&mpw=" + pw + "&mname=" + name) // 서버처리.
				.then(function(result) {
					return result.json();
				})
				.then((result) => {
					console.log(result);
					if (result.retCode == "OK") {
						alert('추가 하시겠습니까?')
					} else if (result.retCde == "NG") {
						alert('추가오류 발생'); // 에러.
					} else {
						alert('알수없는 코드입니다.')
					}
				})
})