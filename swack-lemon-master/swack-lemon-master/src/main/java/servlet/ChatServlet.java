package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.ChatModel;

/**
 * メッセージ投稿を行う機能
 * */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * POSTメソッド
	 * 
	 * つぶやき機能を実行する機能
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータ取得
		String roomId = request.getParameter("roomId");
		String message = request.getParameter("message");
		//新規ルーム作成時にルームIDとシステムメッセージを取得する
		if (roomId == null && message == null) {
			HttpSession session = request.getSession();
			roomId = (String) session.getAttribute("roomId");
			message = (String) session.getAttribute("message");
			//セッションの削除
			session.removeAttribute("roomId");
			session.removeAttribute("message");
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 処理
		try {
			// つぶやく
			new ChatModel().saveChatLog(roomId, user.getUserId(), message);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

	/**
	 * GETメソッド
	 * 
	 * ほかのサーブレットからシステムメッセージを受け取る
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータ取得
		String roomId = request.getParameter("roomId");
		String message = request.getParameter("message");
		//リクエストパラメータに格納されていない場合、
		//セッションパラメータを探索する
		if (roomId == null && message == null) {
			HttpSession session = request.getSession();
			roomId = (String) session.getAttribute("roomId");
			message = (String) session.getAttribute("message");
			session.removeAttribute("roomId");
			session.removeAttribute("message");
		}

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 処理
		try {
			// つぶやく
			new ChatModel().saveChatLog(roomId, user.getUserId(), message);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

}
