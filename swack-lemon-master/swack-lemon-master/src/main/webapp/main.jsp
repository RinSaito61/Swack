<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - メイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/bootstrap-toggle.min.css" rel="stylesheet">
<link href="css/bootstrap-select.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">
<script src="js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-toggle.min.js"></script>
<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
</head>

<body>
			<input type=hidden value=${nowRoom.roomId } id="keepRoomId">
			<input type=hidden value=${nowUser.userId } id="keepUserId">
			<script src="js/keepRoom.js"></script>

	<div class="container">
		<div class="row">
			<div class="col">

				<header class="header">
					<div class="dropdown">
						<a class="dropdown-toggle" id="user-color" data-toggle="dropdown"
							aria-expanded="false">${nowUser.userName}</a>
						<ul class="dropdown-menu" role="menu">
							<li role="presentation"><a>設定</a></li>
							<li role="presentation"><a href="acount.jsp">アカウント</a></li>
						</ul>
					</div>
					<form action="LogoutServlet">
						<input class="btn-swack" id="logout" type="submit" value="ログアウト">
					</form>
				</header>

				<section class="main">
					<div class="left">
						<h2>Swack</h2>

						<details open>
							<summary> ルーム </summary>

							<div class="dropdown">
								<input class="btn btn-default dropdown-toggle" type="button"
									value="+" data-toggle="dropdown" aria-expanded="false">
								<ul class="dropdown-menu" role="menu">
									<li role="presentation"><a href="MainServlet?roomId=rlist">ルーム一覧の表示</a></li>
									<li role="presentation"><a data-toggle="modal"
										data-target="#basicModal">ルームの作成</a></li>
								</ul>
							</div>
							
							<c:forEach var="room" items="${roomList}">
								<c:choose>
									<c:when test="${room.privated }">
										<a class="list-name" href="MainServlet?roomId=${room.roomId}"
											id="privateRoom">@ ${room.roomName} </a>
									</c:when>
									<c:otherwise>
										<a class="list-name" href="MainServlet?roomId=${room.roomId}"
											id="publicRoom"> # ${room.roomName} </a>
									</c:otherwise>
								</c:choose>
								<br>
							</c:forEach>
						</details>

						<div class="modal fade" id="basicModal" tabindex="-1"
							role="dialog" aria-labelledby="basicModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">

									<div class="modal-header">
										<h3 class="modal-title" id="myModalLabel">ルームを作成する</h3>
										<p class="input_note_special medium_bottom_margin">ルームとはメンバーがコミュニケーションを取る場所です。特定のトピックに基づいてルームを作ると良いでしょう
											(例: #営業)。</p>
									</div>

									<div class="modal-body">
										<form action="CreateRoomServlet" method="post">

											<div class="form-group">
												<div>
													<label> <c:choose>
															<c:when
																test="${nowUser.userId==log.userId or nowUser.userId=='U0000' }">
																<input type="checkbox" id="chk" name="chk" checked
																	data-toggle="toggle" data-on="パブリック" data-off="プライベート"
																	data-onstyle="success" data-offstyle="warning">
															</c:when>
															<c:otherwise>
																<input type="checkbox" id="chk" name="chk" checked
																	data-toggle="toggle" data-on="パブリック" data-off="プライベート"
																	data-onstyle="success" data-offstyle="warning"
																	disabled="disabled">
																<input type="hidden" name="chk" value="1" />
															</c:otherwise>

														</c:choose>
													</label> <br> <span class="toggle_label">このルームは、ワークスペースのメンバーであれば誰でも閲覧・参加することができます。</span>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label">名前</label> <input id="name"
													name="name" class="form-control" type="text"
													placeholder="# 例:営業" autofocus> <span
													class="name-note">ルームの名前を入力してください。</span>
											</div>

											<div class="form-group">
												<label class="control-label">招待の送信先:(任意)</label> <select
													class="form-control selectpicker" data-live-search="true"
													data-selected-text-format="count > 1" name="invite"
													multiple>
													<c:forEach var="invite" items="${userList}">
														<option value="${invite.userId}">${invite.userName}</option>
													</c:forEach>
												</select> <span class="users-note">このルームに追加したい人を選んでください。</span>
											</div>

											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">キャンセル</button>
												<button id="send" class="btn btn-default">ルームを作成する</button>
											</div>

										</form>
									</div>
								</div>
							</div>
						</div>

						<details open>
							<summary>ダイレクト</summary>
							<c:forEach var="room" items="${directList}">
								<a class="list-name" href="MainServlet?roomId=${room.roomId}">
									# ${room.roomName} </a>
								<br>
							</c:forEach>
						</details>
					</div>

					<div class="contents">
						<div class="contents-header">
							<h2>${nowRoom.roomName}
								<c:choose>
									<c:when test="${nowRoom.roomId== 'rlist' }">
										<span class="addiction-room">${AddCount}個の未参加ルーム</span>
									</c:when>
									<c:otherwise>(${nowRoom.memberCount}人)								
									<input class="btn-swack" type="button" value="+"
									data-toggle="modal" data-target="#inventModal"></c:otherwise>
								</c:choose>
							</h2>
							<hr>
						</div>

						<div class="modal fade" id="inventModal" tabindex="-1"
							role="dialog" aria-labelledby="inventModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">

									<div class="modal-header">
										<h3 class="modal-title" id="myModalLabel">
											他のユーザを#${nowRoom.roomName}に招待する</h3>
									</div>

									<div class="modal-body">
										<form action="InviteRoomServlet?roomId=${nowRoom.roomId }"
											method="post">

											<div class="form-group">
												<label class="control-label">招待の送信先:</label> <select
													name="invite" id="users" class="form-control selectpicker"
													data-live-search="true"
													data-selected-text-format="count > 1" multiple>
													<c:forEach var="invite" items="${InvitationUserList}">
														<option value="${invite.userId}">${invite.userName}</option>
													</c:forEach>
												</select> <span class="users-note">このルームに追加したい人を選んでください。</span>
											</div>

											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">キャンセル</button>
												<button type="submit" id="sendInvite" class="btn btn-default">招待する</button>
											</div>

										</form>
									</div>
								</div>
							</div>
						</div>
						<div id="logArea" class="contents-area">
							<c:choose>
								<c:when test="${nowRoom.roomId== 'rlist' }">
									<c:forEach var="add" items="${AddList}">
										<div class="log-area">
											<div class="log-add">
												<a class="btn" href="AddUserServlet?roomId=${add.roomId }">
													${add.roomName}</a>
											</div>
										</div>
									</c:forEach>
								</c:when>
								<c:otherwise>

									<c:forEach var="log" items="${chatLogList}">
										<div class="log-area">
											<div class="log-box">
												<span class="log-name">${log.userName} </span> <span
													class="log-time">[${log.createdAt}]</span>
													
												<c:if
													test="${nowUser.userId==log.userId or nowUser.userId=='U0000' }">
													<img src="images/pencil.svg" class="edit pointer" />
													<img src="images/trash.svg" class="delete pointer" />
													<input type="hidden" value="${nowRoom.roomId }"
														class="HiddenroomId">
													<input type="hidden" value="${log.chatLogId }"
														class="HiddenchatLogId">
												</c:if>
												<br> <span class="log-post">${log.message}</span>
											</div>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="contents-footer">
							<form action="ChatServlet" method="post">
								<input type="hidden" name="roomId" value="${nowRoom.roomId}">
								<div class="form-wrap">
									<input id="message" type="text" name="message"> <input
										id="sendButton" type="submit" value="送信" disabled>
								</div>
							</form>
						</div>
					</div>
				</section>

			</div>
		</div>
	</div>

	<script src="js/main.js"></script>
	<script src="js/textcount.js"></script>
	<script src="js/popup.js"></script>
	<script src="js/edit.js"></script>
	<script src="js/delete.js"></script>
	<script src="js/invite.js"></script>
</body>

</html>