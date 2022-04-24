package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import WingsUtil.WingsUtil;
import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.SignupModel;

/**
 * 新規登録を行う機能
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * POSTメソッド
	 * 
	 * Signup.jspから送られてきた氏名、メールアドレス、パスワードをもとに
	 * ユーザの新規登録を行い,メイン画面を表示する
	 * エラー時はSignup.jspに戻る
	 * 
	 * @param user not NULL
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
		String userName = WingsUtil.htmlEscape(request.getParameter("username"));
		String mailAddress = request.getParameter("mailaddress");
		String password = request.getParameter("password");
		
		//パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userName == null || userName.length() == 0) {
			errorMsg.append("氏名が入っていません<br>");
		}
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラーメッセージをコンソールに出力する
			request.setAttribute("errorMsg", errorMsg.toString());
			System.out.println( errorMsg.toString());
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		// 処理
		try {
			// ユーザが登録できるかの確認
			SignupModel signupModel = new SignupModel();
			boolean flag = signupModel.cheakUser(mailAddress);
			if (flag) {
				// 認証失敗
				request.setAttribute("errorMsg", ERR_LOGIN_PARAM_MISTAKE);
				System.out.println( errorMsg.toString());
				request.getRequestDispatcher("signup.jsp").forward(request, response);
				return;
			} else{
				RoomModel roomModel = new RoomModel();
				String userId = signupModel.createUserId();
				//ルーム参加処理
				//everyone参加
				roomModel.joinRoom("R0000", userId);
				//登録するユーザとのダイレクトの作成
				ArrayList<String> userIdList = signupModel.CreateUserIdList();
				for(String userId2 : userIdList) {
					String roomId = roomModel.newRoomId();
					signupModel.InsertDirected(roomId, "P"+userId+","+userId2, "U0000");
					//JoinRoomの追加
					roomModel.joinRoom(roomId, userId);
					roomModel.joinRoom(roomId, userId2);
				}
				User user = signupModel.insertUser(userId, userName, mailAddress, password);
				// 認証成功(情報をセッションに保持)
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet");
				return;
			}
		} catch (SwackException e) {
			//エラーメッセージをコンソールに出力する
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			System.out.println( errorMsg.toString());
			
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
	}

}
