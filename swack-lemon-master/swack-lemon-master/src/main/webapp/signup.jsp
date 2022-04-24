<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - 新規登録画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/signup.css">

</head>
<body>

	<div class="container">
		<h1>Swack</h1>
		<h2>新規登録</h2>
		<div class="card">
			<p class="error" id="errorMsg">${errorMsg}</p>
			<form action="SignupServlet" method="post">
				<input type="text" name="username" placeholder="情報太郎">
				<input type="email" name="mailaddress" placeholder="xxxxxx@xxx.xxx"><br>
				<input type="password" name="password" placeholder="パスワードを入力"><br>
				<input type="submit" value="参加する" ><br>
			</form>
		</div>
		
		<a href="login.jsp">>> ログイン画面へ</a>
	</div>
</body>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/login.js"></script>
</html>