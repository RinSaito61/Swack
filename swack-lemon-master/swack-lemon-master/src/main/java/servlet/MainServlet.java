package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatLog;
import bean.Room;
import bean.User;
import exception.SwackException;
import model.ChatModel;
import model.RoomModel;

/**
 * メイン画面の表示を行う機能
 * */
@WebServlet("/MainServlet")
public class MainServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String roomId = request.getParameter("roomId");
		if (roomId == null) {
			roomId = "R0000";
		}

		try {
			ChatModel chatModel = new ChatModel();
			RoomModel roomModel = new RoomModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId());
			System.out.println(roomList.toString());
			List<Room> directList = chatModel.getDirectList(user.getUserId());
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);
			// 招待できるユーザを保存しているリスト
			List<User> InvitationList = chatModel.getInvitationList(user.getUserId());
			System.out.println(InvitationList.toString());
			// 参加できるルームを保存しているリスト
			List<Room> AddRoomList = roomModel.AddRoomList(user.getUserId());
			//参加できる部屋の個数
			int AddRoomCount = AddRoomList.size();
			// ルームに所属していないメンバーを保存するリスト
			List<User> InvitationUserList = chatModel.getInvitationNowRoomList(roomId);

			// メイン画面を表示する
			// request.setAttribute("フロントで使う変数名",”バックで使う変数名")
			request.setAttribute("nowUser", user);
			request.setAttribute("nowRoom", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);
			request.setAttribute("userList", InvitationList);
			request.setAttribute("AddList", AddRoomList);
			request.setAttribute("AddCount", AddRoomCount);
			request.setAttribute("InvitationUserList", InvitationUserList);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("main.jsp").forward(request, response);

	}

}
