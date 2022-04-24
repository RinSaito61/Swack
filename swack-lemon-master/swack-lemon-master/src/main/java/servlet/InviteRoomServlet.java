package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.SwackException;
import model.RoomModel;

/**
 *ルームにユーザを招待する機能
 */
@WebServlet("/InviteRoomServlet")
public class InviteRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * POSTメソッド
	 * 
	 * Main.jspから送られてきた招待メンバーをルームに追加する
	 * 追加後システムメッセージを招待主の名前でチャットとして出力する
	 * 	エラー時はメイン画面に戻る
	 * 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータ取得
		// 追加先のRoomID取得
		String roomId = request.getParameter("roomId");
		// ユーザーリスト取得
		String[] userList = request.getParameterValues("invite");

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userList == null || userList.length == 0) {
			errorMsg.append("ユーザーが選択されていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			System.out.println(errorMsg.toString());
			response.sendRedirect("MainServlet?roomId=R0000");// everyone画面へ
			return;
		}

		try {

			RoomModel roomModel = new RoomModel();
			// 参加メッセージの作成
			StringBuilder message = new StringBuilder();
			// 招待メンバーの登録
			if (userList!= null) {
				for (String inviteMember :userList) {
					roomModel.joinRoom(roomId, inviteMember);
				}
				message.append(userList.length + "名が参加しました");
			}

			// ChatServletへの遷移
			request.getRequestDispatcher("ChatServlet?roomId=" + roomId + "&message=" + message).forward(request,
					response);
		} catch (SwackException e) {
			// エラー
			request.setAttribute("errorMsg", "招待に失敗しました");
			System.out.println(errorMsg.toString());
			response.sendRedirect("MainServlet?roomId=R0000");
		}

	}

}
