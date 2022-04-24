package model;

import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import bean.User;
import dao.ChatDAO;
import dao.UsersDAO;
import exception.SwackException;

/**
 * チャット機能を実行するModel
 */
public class ChatModel {

	/**表示するルーム情報の取得*/
	public Room getRoom(String roomId, String userId) throws SwackException {
		System.out.println("[getRoom] " + roomId + " " + userId);

		ChatDAO chatDAO = new ChatDAO();
		Room room = null;
		if (roomId.equals("rlist")) {
			room = new Room(roomId, "ルーム一覧");
		} else {
			room = chatDAO.getRoom(roomId, userId);
		}
		return room;
	}

	/**所属しているルームをリストとして取得する処理*/
	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		System.out.println("[getRoomList] " + userId);

		ChatDAO chatDAO = new ChatDAO();

		ArrayList<Room> list = chatDAO.getRoomList(userId);

		return list;

	}

	/**ダイレクトルームをリストとして取得する処理 */
	public ArrayList<Room> getDirectList(String userId) throws SwackException {
		System.out.println("[getDirectList] " + userId);

		ChatDAO chatDAO = new ChatDAO();

		ArrayList<Room> list = chatDAO.getDirectList(userId);

		return list;

	}

	/**チャットログの取得*/
	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		System.out.println("[getChatlogList] " + roomId);
		
		ChatDAO chatDAO = new ChatDAO();

		List<ChatLog> list = chatDAO.getChatlogList(roomId);

		return list;

	}

	/**チャットログの記録を行う処理*/
	public void saveChatLog(String roomId, String userId, String message) throws SwackException {
		System.out.println("[saveChatLog] " + roomId + " " + userId + " " + message);

		ChatDAO chatDAO = new ChatDAO();

		chatDAO.saveChatlog(roomId, userId, message);
	}

	/**自分以外のすべてのユーザーを取得する*/
	public List<User> getInvitationList(String userId) throws SwackException {
		// 招待ユーザリストの作成
		List<User> InvitationList = new ArrayList<User>();
		UsersDAO usersDAO = new UsersDAO();

		// ユーザリストの取得
		InvitationList = usersDAO.getUserList(userId);

		return InvitationList;

	}

	/**ルームに所属していないすべてのユーザーを取得する*/
	public List<User> getInvitationNowRoomList(String roomId) throws SwackException {
		List<User> InvitationNowRoomList = new ArrayList<User>();
		UsersDAO usersDAO = new UsersDAO();
		InvitationNowRoomList = usersDAO.getadditionUser(roomId);
		return InvitationNowRoomList;
	}

	/**チャットの削除を行う処理*/
	public void delete(String chatLogId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		chatDAO.deleteChatlog(chatLogId);
	}
	
	/**チャットの変更を行う処理*/
	public void update(String chatLogId,String message) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		chatDAO.updateChatlog(chatLogId, message);
	}
	
	/**チャットログIDからそのユーザーIDの取得*/
	public String getUserId(String chatLogId) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		String userid =chatDAO.getUserId(chatLogId);
		return userid;
	}
	
	/**チャットの編集を行う処理*/
	public void change(String chatLogId,String message) throws SwackException {
		ChatDAO chatDAO = new ChatDAO();
		chatDAO.updateChatlog(chatLogId, message);
	}

}
