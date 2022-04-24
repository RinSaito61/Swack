package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bean.ChatLog;
import bean.Room;
import exception.SwackException;

class ChatDAOTest {

	@Test
	void testGetRoom() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		Room room = chatDAO.getRoom("R0003", "U0001");

		// 結果の確認
		System.out.println("testGetRoom()");
		System.out.println(room.toString());

	}

	//@Test
	void testGetRoomList() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		ArrayList<Room> roomlist
			= chatDAO.getRoomList("U0001");

		// 結果の確認
		System.out.println("testGetRoomList()");
		System.out.println(roomlist.toString());

	}

	//@Test
	void testGetDirectList() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		ArrayList<Room> roomlist
			= chatDAO.getDirectList("U0001");

		// 結果の確認
		System.out.println("testGetDirectList()");
		System.out.println(roomlist.toString());

	}

	//@Test
	void testGetChatlogList() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		List<ChatLog> chatloglist
			= chatDAO.getChatlogList("R0000");
		
		// 結果の確認
		System.out.println("testGetChatlogList()");
		System.out.println(chatloglist.toString());
	}

	
	//@Test
	void testSaveChatlog() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		chatDAO.saveChatlog("R0000", "U0001", "今日は雨です！！！");
		
		// 結果の確認(DBの直接確認)
		System.out.println("testSaveChatlog()");

	}
	
	//@Test
	void testDeleteChatlog() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		chatDAO.deleteChatlog("31");
		
		// 結果の確認(DBの直接確認)
		System.out.println("testDeleteChatlog()");

	}
	
	//@Test
	void testUpdateChatlog() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		chatDAO.updateChatlog("31","aaaaa");
		
		// 結果の確認(DBの直接確認)
		System.out.println("testUpdateChatlog()");
	}
	
	//@Test
	void testGetUserId() throws SwackException {
		// 準備
		ChatDAO chatDAO = new ChatDAO();

		// テスト実行
		String userId = chatDAO.getUserId("1");
		
		// 結果の確認(DBの直接確認)
		System.out.println("testGetUserId()");
		System.out.println(userId);
	}


}
