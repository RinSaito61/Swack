package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import WingsUtil.WingsUtil;
import exception.SwackException;
import model.ChatModel;

/**
 * メッセージの編集を行う機能
 */
@WebServlet("/ChatChangeServlet")
public class ChatChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GETメソッド
	 * 
	 * Main.jspからルームIDと変更するチャットのIDを取得し、変更を行う
	 * 成功時、エラー時ともにメイン画面に遷移
	 * エラーメッセージはコンソールのみに表示
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータの取得
		String roomId = request.getParameter("roomId");/* MainServletへの遷移時に利用 */
		String chatLogId = request.getParameter("chatLogId");
		String message = WingsUtil.htmlEscape(request.getParameter("message"));
		StringBuilder errorMsg = new StringBuilder();
		
		//パラメータチェック
		if (message.length() == 0 || message.equals("")) {
			errorMsg.append("メッセージが入力されていません");
		}
		if (errorMsg.length() > 0) {
			System.out.println(errorMsg.toString());
			response.sendRedirect("MainServlet?roomId=" + roomId);
			return;
		}
		
		// ChatModelのインポート
		ChatModel chatModel = new ChatModel();
		try {
			// 変更の登録
			chatModel.change(chatLogId, message);
		} catch (SwackException e) {
			// コンソールにエラーメッセージを表示
			StringBuilder messages = new StringBuilder();
			messages.append("メッセージの編集ができませんでした");
			System.out.println(messages.toString());
		}
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

}
