/**
 * ワークスペースに自動でログインできるかチェック
 */
$(window).on('load', function() {
	$('body').css('display', 'none');
	//エラーメッセージの有無の確認
	if ($('#errorMsg').text().length == 0) {
		// ローカルストレージからログインデータの取得
		var oldData = localStorage.getItem('loginData');
		//データの有無の確認
		if (oldData) {
			// 文字列に変換
			var realData = JSON.parse(oldData);
			//オートログインにチェックされているか
			if (realData.autoLogin) {
				$('#mailAddress').val(realData.mailAddress);
				$('#password').val(realData.password);
				$('#loginForm').submit();
			} else {
				$('body').css('display', 'block');
			}
		} else {
			$('body').css('display', 'block');
		}
	} else {
		localStorage.removeItem('loginData');
		$('body').css('display', 'block');
	}
});

/**
* ログイン時、チェックされていたら自動ログインする
*/
function login() {
	var save = $('#save').prop('checked');
	// チェックされているか
	if (save) {
		var loginData = {
			mailAddress: $('#mailAddress').val(),
			password: $('#password').val(),
			autoLogin: save
		};
		//ローカルストレージに登録
		localStorage.setItem('loginData', JSON.stringify(loginData));
	}
}