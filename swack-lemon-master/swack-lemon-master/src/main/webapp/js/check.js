'use strict';
function check(){
    const message = $('#message-text').val();
    if(message === ""){
        alert('メッセージが未入力です');
        return false;
    }
    alert('「' + message +'」を送信');
    return true;
}
