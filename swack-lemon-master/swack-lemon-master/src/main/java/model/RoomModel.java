package model;

import java.util.ArrayList;
import java.util.List;

import bean.Room;
import dao.RoomDAO;
import exception.SwackException;

/**
 * チャット機能を実行するModel
 */
public class RoomModel {

	/**新規ルームのDBへの登録*/
	public void createRoom(String roomId, String roomName, String createdUserid, boolean privated) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();

		// 登録処理でエラーが出た場合分岐する
		try {
			// 新規ルームのDBへの登録
			roomDAO.setRoom(roomId, roomName, createdUserid, false, privated);
		} catch (SwackException e) {
		}
		return;
	}

	/**新しいRoomIDの作成*/
	public String newRoomId() throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		String newRoomId = "";

		// 最大のユーザIDの取得
		String max_roomId = roomDAO.selectMaxRoomId();

		// 新規ルームのルームIDの作成
		int giveId = Integer.parseInt(max_roomId.replaceAll("[^0-9]", "")) + 1;
		newRoomId = "R" + String.format("%04d", giveId);

		return newRoomId;
	}
	
	/**ルームへの参加*/
	public void joinRoom(String roomId,String userId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		roomDAO.setJoinRoom(roomId, userId);
	}
	
	/**
	 * 同一のルーム名が登録されているかの確認
	 *  参加している場合はTrue,参加していない場合はFalseを返す
	 */
	public boolean CheakRoomName(String roomName) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		boolean chkCnt = roomDAO.checkRoom(roomName);
		return chkCnt;
	}
	
	/**
	 * 指定ルームにすでに参加していないかの確認
	 * 
	 * 参加している場合はTrue,参加していない場合はFalseを返す
	 */
	public boolean CheakJoinRoom(String roomId, String userId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		boolean chkCnt = roomDAO.checkJoinRoom(roomId,userId);
		return chkCnt;
	}
	
	/**プライベートルーム以外の自身が所属しているルームを取得する*/
	public List<Room> AddRoomList(String userId) throws SwackException{
		List<Room> addRoomList = new ArrayList<Room>();
		RoomDAO roomDAO = new RoomDAO();
		addRoomList =roomDAO.getRoomList(userId);
		return addRoomList;
		
	}
}
