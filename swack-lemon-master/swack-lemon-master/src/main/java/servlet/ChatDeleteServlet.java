package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.SwackException;
import model.ChatModel;

/**
 * メッセージの削除を行う機能
 */
@WebServlet("/ChatDeleteServlet")
public class ChatDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * GETメソッド
	 * 
	 * Main.jspからルームIDと削除するチャットのIDを取得し、削除を行う
	 * 成功時、エラー時ともにメイン画面に遷移
	 * エラーメッセージはコンソールのみに表示
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータの取得
		String roomId = request.getParameter("roomId");/* MainServletへの遷移時に利用 */
		String chatLogId = request.getParameter("chatLogId");
		try {		// チャットの削除
			ChatModel chatModel = new ChatModel();
			chatModel.delete(chatLogId);
		} catch (SwackException e) {
			//コンソールにエラーメッセージを表示
			StringBuilder message = new StringBuilder();
			message.append("メッセージの削除ができませんでした");
			System.out.println(message.toString());
		}
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

}
