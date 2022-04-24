/**
 * 
 */
$(window).on('load', function(){ 
	//alert('Hello!!!!');
	$("#logArea").scrollTop($("#logArea")[0].scrollHeight)
 });

/**
* ログアウト時ローカルストレージ内の情報の削除
 */
$('#logout').on('click', function(){ 
	localStorage.removeItem("loginData");
 });