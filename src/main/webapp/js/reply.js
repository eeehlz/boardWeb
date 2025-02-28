/**
 * reply.js
 */
console.log(svc.showName());
let page = 1; //페이징.

//댓글.
function makeReply(reply = {}) {
	let html = `<li data-id="${reply.replyNo}">
				<span class="col-sm-2">${reply.replyNo}</span>
				<span class="col-sm-5">${reply.reply}</span>
				<span class="col-sm-2">${reply.replyer}</span>
				<span class="col-sm-2"><button type="button" class="btn btn-dark" onclick="deleteRow('${reply.replyNo}')">삭제</button></span>
			  </li>`;
	return html;
}
//글삭제
function deleteRow(rno) {
	if (!confirm("삭제하겠습니까?")) {
		alert('취소합니다.');
		return;
	}
	svc.removeReply(rno
		, function(result) {
			if (result.retCode == 'OK') {
				document.querySelector('li[data-id="' + rno + '"]').remove();
				
				let nowPage = document.querySelectorAll('.reply>.content>ul>li').length;
				if (nowPage ===0 && page>1){
					page --;
				}				
				document.querySelector('.reply>.content>ul').innerHTML = '';
				
				showPageList();
				showPagingList();
			} else {
				alert('처리 실패, 잠시 후 다시 시도하세요');
			}
		}
		, function(err) { console.log(err); })
}//end of deleteRow

//댓글 목록 출력함수,
function showPageList() {
	svc.replyList({ bno, page }, //원본글번호
		//성공함수,
		function(result) {
			//기존목록 지우기
			document.querySelectorAll('li[data-id]').forEach(function(elem) {
				elem.remove();
			})
			let resultAry = result;
			resultAry.forEach(function(reply) {
				let target = document.querySelector('.reply>.content>ul');
				target.insertAdjacentHTML('beforeend', makeReply(reply));
			});
		},
		//실패함수
		function(err) {

			console.log(err);
		}
	);
}
// 목록.
showPageList();
showPagingList();


//페이징 생성.
function showPagingList() {	
  svc.makePaging(bno,
  	function(result) {
	console.log(result); // {totalCnt:158}
	const totalCnt = result.totalCnt;
// startPage, endPage, currPage
// prev, next 계산.  1 .. 5 .. 10
	let currPage = page;
	let endPage = Math.ceil(currPage / 10) * 10;
	let startPage = endPage - 9;
	let realEnd = Math.ceil(totalCnt / 5);
	endPage = endPage > realEnd ? realEnd : endPage;
	let prev = startPage != 1 ? true : false;
	let next = endPage != realEnd ? true : false;

// 링크 생성.
	let target = document.querySelector('div.footer>nav>ul');
	target.innerHTML = '';//이전 페이징 정보 삭제.	
	// 이전 페이지.
	let html = '';
	if (prev) {
		html = `<li class="page-item">
		      	<a class="page-link" href="#" data-page="${startPage - 1}">Previous</a>
		    	</li>`;
	} else {
		html = `<li class="page-item disabled">
		    	<a class="page-link">Previous</a>
		    	</li>`;
	}
	target.insertAdjacentHTML('beforeend', html);
	//현재 페이지.
	for (let p = startPage; p <= endPage; p++) {
		if(p == currPage){
	 html = `<li class="page-item active"><a class="page-link" href="#" data-page="${p}">${p}</a></li>`;
	}else{
	 html = `<li class="page-item"><a class="page-link" href="#" data-page="${p}">${p}</a></li>`;
	}
		target.insertAdjacentHTML('beforeend', html);
	}
	//이후 페이지. 
	if (next) {
		html = `<li class="page-item">
				<a class="page-link" href="#" data-page="${endPage + 1}">Next</a>
				</li>`;
	} else {
		html = `<li class="page-item disabled">
				<a class="page-link">Next</a>
		     	</li>`;
	}
	target.insertAdjacentHTML('beforeend', html);
	//event.
	addLinkEvent(); // 화면a태그에 이벤트 등록.
},
function(err) {
	console.log(err);	
	}
  );
} // end of showPageList


//이벤트 등록 
// 댓글등록 이벤트 . id="addReply"
document.querySelector('#addReply').addEventListener('click', function() {
	//글번호 : bno, 작성자 : logid , 댓글: ? 
	// id = "reply" -input # 
	const reply = document.querySelector('#reply').value;
	const replyer = logid;
	if (!reply || !replyer) {
		alert('필수입력값을 확인하세요.');
		return;
	}
	const parm = { bno, reply, replyer }

	svc.addReply(parm//
		, function(result) {
			if (result.retCode == 'OK') {
				//const html = makeReply(result.retVal);
				//let target = document.querySelector('.reply>.content>ul');
				//target.insertAdjacentHTML('beforeend', html);
				page = 1;
				showPageList();
				showPagingList();
				//입력항목 지워주기
				document.querySelector('#reply').value='';
			} else {
				alert('처리 실패, 잠시 후 다시 시도하세요');
			}
		}//
		, function(err) { console.log(err); });

})//end of event

//페이징목록의 링크() 이벤트 [a, a, a, a, a, a, a, .... a] 
function addLinkEvent() {
	document.querySelectorAll(`div.footer>nav a`).forEach(function(item) {
		item.addEventListener('click', function(e) {
			e.preventDefault(); // 기본기능차단		
			console.log(e.target.getAttribute('data-page'));
			page = e.target.getAttribute('data-page');//링크클릭하면 페이지 전환
			//목록보기
			showPageList();
			//페이지생성.
			showPagingList();
		});
	});
}
showPagingList();





