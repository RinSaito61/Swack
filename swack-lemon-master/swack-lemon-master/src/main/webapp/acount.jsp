<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - acount settings</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/bootstrap-toggle.min.css" rel="stylesheet">
<link href="css/bootstrap-select.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/acount.css">
</head>

<body>
	<div class="container">
		<div class="acount-flex">
			<img src="./images/useracounticon.svg" class="acount-icon" id="userimg" />
			<h3>アカウント</h3>
		</div>

		<div class="card">
				<form action="UpdatePasswordServlet" id="settingForm" method="post">
				<h4>パスワード</h4>
						<hr>
						<p class="error" id="errorMsg">${errorMsg}</p>
					<div class="cardbody">
						<p>現在のパスワード</p>
							<input type="password" name="nowPassWord" id="password" placeholder="現在のパスワードを入力">
						<p>新規パスワード</p>
							<input type="password" name="newPassWord" placeholder="新規パスワードを入力"><br>
							<input type="submit" value="変更">
					</div>
				</form>
		</div>
		
		<a href="MainServlet">←Swackに戻る</a>
	</div>
</body>

<script src="js/jquery-3.2.0.min.js"></script>

</html>