package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import exception.SwackException;

/**
 * チャット機能に関するDBアクセスを行う.
 */
public class ChatDAO {

	/** Room情報取得するDAO	 */
	public Room getRoom(String roomId, String userId) throws SwackException {
		String sqlGetRoom = "SELECT R.ROOMID, R.ROOMNAME, COUNT(*) AS MEMBER_COUNT, R.DIRECTED FROM ROOMS R JOIN JOINROOM J ON R.ROOMID = J.ROOMID WHERE R.ROOMID = ? GROUP BY R.ROOMID, R.ROOMNAME, R.DIRECTED";
		String sqlGetDirectRoom = "SELECT U.USERNAME AS ROOMNAME FROM JOINROOM R JOIN USERS U ON R.USERID = U.USERID WHERE R.USERID <> ? AND R.ROOMID = ?;";

		boolean directed = false;
		String roomName = "";
		int memberCount = 0;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlGetRoom);
			pStmt.setString(1, roomId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				directed = rs.getBoolean("DIRECTED");
				roomName = rs.getString("ROOMNAME");
				memberCount = rs.getInt("MEMBER_COUNT");
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// for Direct
		if (directed) {
			try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
				PreparedStatement pStmt = conn.prepareStatement(sqlGetDirectRoom);
				pStmt.setString(1, userId);
				pStmt.setString(2, roomId);

				ResultSet rs = pStmt.executeQuery();
				rs.next();
				roomName = rs.getString("ROOMNAME");
				memberCount = 2;

			} catch (SQLException e) {
				throw new SwackException(ERR_DB_PROCESS, e);
			}
		}

		Room room = new Room(roomId, roomName, memberCount, directed);
		return room;
	}

	/** ユーザーIDからRoomList作成するDAO */
	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		// SQL
		String sql = "SELECT R.ROOMID, R.ROOMNAME,R.PRIVATED FROM JOINROOM J JOIN ROOMS R ON J.ROOMID = R.ROOMID WHERE J.USERID = ? AND R.DIRECTED = FALSE";

		ArrayList<Room> roomlist = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				boolean privated = rs.getBoolean("PRIVATED");

				Room room = new Room(roomId, roomName,privated);
				roomlist.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;

	}

	/** ユーザーIDからダイレクトRoomListの作成するDAO */
	public ArrayList<Room> getDirectList(String userId) throws SwackException {
		// SQL
		String sql = "SELECT R.ROOMID, U.USERNAME AS ROOMNAME FROM JOINROOM R JOIN USERS U ON R.USERID = U.USERID WHERE R.USERID <> ? AND ROOMID IN (SELECT R.ROOMID FROM JOINROOM J JOIN ROOMS R ON J.ROOMID = R.ROOMID WHERE J.USERID = ? AND R.DIRECTED = TRUE) ORDER BY R.USERID";

		ArrayList<Room> roomlist = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setString(2, userId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");

				Room room = new Room(roomId, roomName);
				roomlist.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;

	}

	/** RoomIDからそのRoomのログを取得するDAO*/
	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		// SQL
		String sql = "SELECT CHATLOGID, U.USERID AS USERID, U.USERNAME AS USERNAME, MESSAGE, CREATED_AT "
				+ "FROM CHATLOG C JOIN USERS U ON C.USERID = U.USERID WHERE ROOMID = ? " + "ORDER BY CREATED_AT ASC";

		List<ChatLog> chatLogList = new ArrayList<ChatLog>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();
			
			// 結果を詰め替え
			while (rs.next()) {
				int chatLogId = rs.getInt("CHATLOGID");
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				String message = rs.getString("MESSAGE");
				Timestamp createdAt = rs.getTimestamp("CREATED_AT");

				ChatLog chatLog = new ChatLog(chatLogId, roomId, userId, userName, message, createdAt);
				chatLogList.add(chatLog);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return chatLogList;

	}

	
	/** メッセージをDBに保存するDAO */
	public void saveChatlog(String roomId, String userId, String message) throws SwackException {
		// SQL
		String sql = "INSERT INTO CHATLOG (CHATLOGID, ROOMID, USERID, MESSAGE, CREATED_AT) VALUES (nextval('CHATLOGID_SEQ'), ?, ?, ?,  CURRENT_TIMESTAMP)";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);
			pStmt.setString(3, message);

			// SQL実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	/**チャットログの削除するDAO*/
	public void deleteChatlog(String chatlogId) throws SwackException {
		// SQL
		String sql = "DELETE FROM chatlog WHERE chatlogid = ? ";
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, Integer.parseInt(chatlogId));
			
			// SQL実行
			pStmt.executeUpdate();
			

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	/**
	 * チャットログの変更するDAO
	 */
	public void updateChatlog(String chatlogId ,String message) throws SwackException {
		// SQL
		String sql = "UPDATE chatlog SET message = ?  WHERE chatlogid = ? ;";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, message);
			pStmt.setInt(2, Integer.parseInt(chatlogId));

			// SQL実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}
	
	/**
	 * チャットログのユーザーIDを取得するDAO
	 */
	public String getUserId(String chatlogId) throws SwackException {
		// SQL
		String sql = "SELECT UserId FROM chatlog WHERE chatlogid= ?";
		
		String userId;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, Integer.parseInt(chatlogId));
			
			// SQL実行
			ResultSet rs = pStmt.executeQuery();
			
			// 結果の取得
			rs.next();
			userId = rs.getString("USERID");
			
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return userId;
	}
	
}
