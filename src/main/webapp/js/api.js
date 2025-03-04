/*
* api.js
*/
let centerAll = [];

//이벤트(select태그에 change) 등록.
document.getElementById('centerList').addEventListener('change', function(e) {
	let sidoName = e.target.value;
	console.log(centerAll, sidoName);

	let filterSido = [];
	filterSido = centerAll.filter(item => {
		if (item.sido == sidoName) {
			return true;
		}
		return false;
	});
	console.log(filterSido);
	makeCenterList(filterSido);
})

function makeCenterList(centerAry = []) {
	let fields = ['id', 'centerName', 'phoneNumber', 'sido'];
	//기존목록 삭제
	document.getElementById('list').innerHTML = '';
	//센터정보
	centerAry.forEach(center => {
		//tr>td*4
		let tr = document.createElement('tr');
		tr.addEventListener('click', function() {
			console.log(center.lat, center.lng);
			window.open('map.do?lat=' + center.lat + '&lng=' + center.lng + '&cname=' + center.centerName);
		})
		for (let i = 0; i < fields.length; i++) {
			let td = document.createElement('td');
			td.innerHTML = center[fields[i]];
			tr.appendChild(td);
		}
		document.getElementById('list').appendChild(tr);
	});
};

//Ajax.
fetch('https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=284&returnType=JSON&serviceKey=4Egoesd4TCtVm3HJBHaPWGbbXEqHfL%2FE9Pd%2BDOuRbcsxfICDS56dzWtPwAdxjM0znW3MthIMqekQP8LCSbjyjQ%3D%3D')
	.then(result => result.json())
	.then(result => {
		console.log(result.data);
		centerAll = result.data;
		makeSidoList();
	})
	.catch(err => console.log(err));

// 시도정보 중복제거 후 화면 출력.
function makeSidoList() {
	let sidoList = []; // ['서울특별시', '인천광역시', '대전광역시', '광주광역시' ...]
	for (let i = 0; i < centerAll.length; i++) {
		if (sidoList.indexOf(centerAll[i].sido) == -1) {
			sidoList.push(centerAll[i].sido);
		}
	}
	console.log(sidoList.sort());
	sidoList.forEach(sido => {
		let opt = document.createElement('option');
		opt.innerHTML = sido; // <option>서울특별시</option>
		document.getElementById('centerList').appendChild(opt);
	})
}