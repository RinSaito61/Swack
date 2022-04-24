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
import model.UserModel;

/**
 * パスワードの変更を行う機能
 */
@WebServlet("/UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * POSTメソッド
	 * 
	 * acount.jspで入力されたパスワードと照合しあっていた場合、
	 * 新規パスワードへの更新を行う
	 * 成功時はメイン画面、エラー時はアカウント画面に遷移する
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserModel userModel = new UserModel();
		
		// ユーザーID取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userId= user.getUserId();
		
		// 現在パスワード取得
		String nowPassWord = request.getParameter("nowPassWord");
		
		// 新規パスワード取得
		String newPassWord = request.getParameter("newPassWord");

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userId == null || userId.length() == 0) {
			errorMsg.append("ユーザーIDが入力されていません<br>");
		}
		if (nowPassWord == null || nowPassWord.length() == 0) {
			errorMsg.append("現在のパスワードが入力されていません<br>");
		}
		if (newPassWord == null || newPassWord.length() == 0) {
			errorMsg.append("新しいパスワードが入力されていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			System.out.println(errorMsg.toString());
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("acount.jsp").forward(request, response);
			return;
		}
		
		try {
			
			// 現在パスワードの合否パスワード取得
			String oldPassWord = userModel.getPassword(userId);
			
			//現在パスワードの確認
			if (nowPassWord.equals(oldPassWord))  {
				//合っている場合
				userModel.updatePassword(userId, newPassWord);
				System.out.println(userId+"のパスワード変更成功");
				response.sendRedirect("MainServlet");
			}else {
				//間違えている場合
				errorMsg.append("現在パスワードが違います<br>");
				System.out.println(errorMsg.toString());
				request.setAttribute("errorMsg", errorMsg.toString());
				request.getRequestDispatcher("acount.jsp").forward(request, response);
			}
		} catch (SwackException e) {
			// エラー
			request.setAttribute("errorMsg", "パスワード変更に失敗しました");
			System.out.println(errorMsg.toString());
			request.setAttribute("errorMsg", errorMsg.toString());
			response.sendRedirect("acount.jsp");
		}
	}

}
