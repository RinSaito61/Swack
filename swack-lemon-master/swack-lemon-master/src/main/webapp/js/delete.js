'use strict';
$('body').on('click', '.delete', function(){
    const result = window.confirm("削除しますか？");
    if(result){
	const roomId = $(this).nextAll().val();
	const chatId = $(this).nextAll(".HiddenchatLogId").val();
	location.href='ChatDeleteServlet?roomId=' + roomId + '&chatLogId=' + chatId
    }
})