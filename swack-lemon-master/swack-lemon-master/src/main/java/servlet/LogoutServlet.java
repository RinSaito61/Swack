package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ログアウト機能
 * */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッション情報の破棄
		request.getSession().invalidate();

		// ログイン画面を表示
		response.sendRedirect("login.jsp");

	}

}
