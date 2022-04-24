/**
*ルーム情報を保存するJS
*/ 

var user = $('#keepUserId').val();

if (location.search === null || location.search === "") {
	goRoomId = localStorage.getItem(user);
	if (goRoomId) {
		location.href = 'MainServlet?roomId=' + goRoomId;
	}
} else {
	var keepRoomId = $('#keepRoomId').val();
	if(keepRoomId != 'rlist'){
			localStorage.setItem(user, keepRoomId);
	}
}

//$('#logout').on('click', function() {
//	localStorage.removeItem(user);
//})