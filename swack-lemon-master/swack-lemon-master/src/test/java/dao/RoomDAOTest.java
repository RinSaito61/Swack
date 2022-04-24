package dao;

import java.util.List;

import bean.Room;
import exception.SwackException;

public class RoomDAOTest {

	//@Test
	void testSetRoom() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		roomDAO.setRoom("R1000", "testRoom", "U0000", false, false);

		// 結果の確認(DBの直接確認)
		System.out.println("testSetRoom()");
	}
	
	// @Test
	void testSelectMaxRoomId() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		String roomId = roomDAO.selectMaxRoomId();

		// 結果の確認
		System.out.println("testSelectMaxRoomId()");
		System.out.println(roomId);
	}
	
	//@Test
	void testGetRoomList() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		List<Room> roomList = roomDAO.getRoomList("U0004");

		// 結果の確認
		System.out.println("testGetRoomList()");
		System.out.println(roomList.toString());
	}
	
	// @Test
	void testSetJoinRoom() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		roomDAO.setJoinRoom("R1000", "U0000");

		// 結果の確認(DBの直接確認)
		System.out.println("testSetJoinRoom()");
	}
	
	//@Test
	void testCheckRoomm() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		boolean flag = roomDAO.checkRoom("everyone");

		// 結果の確認
		System.out.println("testCheckRoomm()");
		System.out.println(flag);
	}
	
	// @Test
	void testCheckJoinRoom() throws SwackException {
		// 準備
		RoomDAO roomDAO = new RoomDAO();

		// テスト実行
		boolean flag = roomDAO.checkJoinRoom("R0001", "U0001");

		// 結果の確認
		System.out.println("testCheckJoinRoom()");
		System.out.println(flag);
	}

}
