/**
 * member.js
 */
//삭제함수
function deleteRow(id) {
	console.log(id);
	let btn = this;
	fetch("removeMember.do?mid=" + id) //서버처리, 
		.then(function(result) {
			return result.json();
		})
		.then((result) => {
			console.log(result);
			if (result.retCode == "OK") {
				document.querySelector('#tr_' + id).remove(); // 지우기
			} else if (result.retCode == "NG") {
				alert(`삭제오류 발생`); //에러
			} else {
				alert(`알 수 없는 코드입니다.`);
			}
		})
}// end of function
function make() {
	fetch("testData.do")
		.then(function(result) {
			return result.json(); //stream - > object
		})
		.then(function(result) {
			const memberAry = result;
			memberAry.forEach(function(member) {
				console.log(member);
				const target = document.querySelector('#list');
				const html = `<tr id="tr_${member.memberId}">
		<td>${member.memberId}</td>
		<td>${member.passwd}</td>
		<td>${member.memberName}</td>
		<td>${member.responsibility}</td>
		<td><button onclick="deleteRow('${member.memberId}')" class="btn btn-danger">삭제</button></td>
		</tr>`;
				target.insertAdjacentHTML('beforeend', html);
			});
		})
}
//추가 이벤트.
document.querySelector('#addMember').addEventListener('click', function(e) {

	let id = document.querySelector('input[name=mid]').value;
	let pw = document.querySelector('input[name=mpw]').value;
	let name = document.querySelector('input[name=mname]').value;

	fetch("addMember.do?mid=" + id + "&mpw=" + pw + "&mname=" + name)
		.then(function(result) {
			return result.json()
		})
		.then((result) => {
			if (result.retCode == "OK") {
				document.querySelector('#list').innerHTML = '';
				make();
			} else if (result.retCode == "NG") {
				alert('오류');
			} else {
				alert('알수 없는 코드입니다.');
			}
		})
})
make();
/*
document.querySelector('#addMember').addEventListener('click', function(e) {
	let id = document.querySelector("input[name=mid]").value
	let pw = document.querySelector("input[name=mpw]").value
	let name = document.querySelector("input[name=mname]").value

	fetch("addMember.do?mid=" + id + "&mpw=" + pw + "&mname=" + name)
		.then(function(result) {
			return result.json(); //stream - > object
		})
		.then((result) => {
			console.log(result);
			if (result.retCode == "OK") {
				make();
			} else if (result.retCode == "NG") {
				alert(`삭제오류 발생`); //에러
			} else {
				alert(`알 수 없는 코드입니다.`);
			}
		})
})

	fetch("addMember.do?mid=" + id + "&mpw=" + pw + "&mname=" + name)
		.then(function(result) {
			return result.json()
		}).then((result) => {
			console.log(result);
			if (result.retCode == 'OK') {
				alert('성공');
				let target = document.querySelector("tbody#list");
				let html = `<tr id=tr_${id}> 
<td>${id}</td>
<td>${pw}</td>
<td>${name}</td>
<td>User</td>
<td><button onclick="deleteRow('${id}')" class="btn btn-danger">삭제</button></td> 
</tr>`;
				target.insertAdjacentHTML('beforeend', html);
			} else if (result.retCode == "NG") {
				alert('등록 오류')
			} else {
				alert('알 수 없음')
			}
		})
})*/

