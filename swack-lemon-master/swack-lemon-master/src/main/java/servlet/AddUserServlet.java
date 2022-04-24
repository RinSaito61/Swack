package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.RoomModel;

/**
 *選択したルームにユーザーを追加させる機能
 */

/*
 * 機能概要
 * 
 *フロントはボタンプッシュ時にルームIDを渡す
 *バックはユーザー追加後選択したルームを
 *表示するようにMainServletに遷移する
 * */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //パラメータの取得
		String roomId = request.getParameter("roomId");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			//ルーム参加処理
			RoomModel roomModel = new RoomModel();
			//すでに参加しているルームに参加しようとしていないかの確認
			if(!roomModel.CheakJoinRoom(roomId, user.getUserId())) {
				roomModel.joinRoom( roomId,user.getUserId());
				//参加アナウンスの生成
				StringBuilder message = new StringBuilder();
				message.append(user.getUserName() + "が参加しました");
				//ChatServletへの遷移
				request.getRequestDispatcher("ChatServlet?roomId=" + roomId + "&message=" + message.toString()).forward(request, response);
			}else {
				//コンソールにエラーメッセージを表示
				request.setAttribute("errorMsg", "このルームにはすでに参加しています");
				System.out.println(" このルームにはすでに参加しています");
				response.sendRedirect("MainServlet?roomId=R0000");
			}
		} catch (SwackException e) {
			e.printStackTrace();
		}
	}

}
