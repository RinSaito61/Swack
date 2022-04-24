package servlet;

import java.io.IOException;

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

/**
 * ルームの作成を行う機能
 * */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータ取得
		String roomName = WingsUtil.htmlEscape(request.getParameter("name"));
		String privateFlag = request.getParameter("chk");
		String[] invitemembers = request.getParameterValues("invite");
		// ログイン中のユーザの取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		RoomModel roomModel = new RoomModel();

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (roomName == null || roomName.length() == 0) {
			errorMsg.append("ルーム名が入っていません<br>");
		}
		if(user == null) {
			errorMsg.append("ログインしてください<br>");
		}
		try {
			if(roomModel.CheakRoomName(roomName)) {
				errorMsg.append("ルーム名が重複しています");
			}
		} catch (SwackException e1) {
			errorMsg.append("エラーが発生しました");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			System.out.println(errorMsg.toString());
			response.sendRedirect("MainServlet?roomId=R0000");
			return;
		}
		//ルーム区分の判定
		boolean pFlag = false;
		if(privateFlag == null) {
			pFlag = true;
		}
		

		String roomId = "R0000";
		try {
			// 新規ルームIDを取得
			roomId = roomModel.newRoomId();
			// userからuserIdの取得
			String userId = user.getUserId();
			
			// ルームの登録
			roomModel.createRoom(roomId, roomName, userId,pFlag);
			//ルームへの参加
			roomModel.joinRoom(roomId, userId);
			//参加メッセージの作成
			StringBuilder message = new StringBuilder();
			//招待メンバーの登録
			if(invitemembers != null) {
				for(String inviteMember : invitemembers) {
					roomModel.joinRoom(roomId, inviteMember);
				}
				message.append("他" + invitemembers.length + "名と一緒に、" );
			}
			message.append("#" +  roomName + "に参加しました");
			
			//スコープの生成
			session.setAttribute("roomId", roomId);
			session.setAttribute("message", message.toString());
			request.getRequestDispatcher("ChatServlet").forward(request, response);
		} catch (SwackException e) {
			// エラー
			request.setAttribute("errorMsg", "登録に失敗しました");
			System.out.println(errorMsg.toString());
			response.sendRedirect("MainServlet?roomId=R0000");
		}

	}
}
