package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Room;
import exception.SwackException;

/**
 * Room作成機能に関するDBアクセスを行う
 */
public class RoomDAO {

	/** Roomを追加するSQL */
	public boolean setRoom(String roomid, String roomname, String createduserid, boolean directed, boolean privated ) throws SwackException {

		// SQL
		String sql = "INSERT INTO ROOMS (ROOMID, ROOMNAME, CREATEDUSERID, DIRECTED, PRIVATED) VALUES (?, ?, ?, ?, ?)";
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomid);
			pStmt.setString(2, roomname);
			pStmt.setString(3, createduserid);
			pStmt.setBoolean(4, directed);
			pStmt.setBoolean(5, privated);
			System.out.println(roomid+roomname+createduserid+directed+privated);

			// SQLの実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return true;

	}
	
	/** 値が一番大きいRoomIDを取得するDAO */
	public String selectMaxRoomId() throws SwackException {
		//  SQL
		String sql = "SELECT max(roomid) AS max_roomid FROM rooms;";

		String maxRoomId = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			rs.next();
			maxRoomId = rs.getString("max_roomid");
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return maxRoomId;
	}
	
	/** UserIDからそのユーザーが所属していないルームのRoomListを作成するDAO */
		public  List<Room> getRoomList(String userId) throws SwackException {
		//  SQL

		String sql = "SELECT ROOMID,ROOMNAME FROM ROOMS R WHERE R.privated='false' AND R.roomid NOT IN ( SELECT Rs.roomid FROM rooms Rs JOIN JOINROOM J ON Rs.roomid = J.roomid WHERE J.userid = ?);";

		ArrayList<Room> roomlist = new ArrayList<Room>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				
				Room room = new Room(roomId, roomName);
				roomlist.add(room);
			}
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomlist;
	}
		
		/** JOINROOMにデータを追加するDAO */
		public boolean setJoinRoom(String roomId, String userId) throws SwackException {
			//  SQL
			String sql = "INSERT INTO JOINROOM (ROOMID, USERID) VALUES (?, ?);";

			// Access DB
			try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
				// SQL作成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, roomId);
				pStmt.setString(2, userId);

				// SQLの実行
				pStmt.executeUpdate();
				
			} catch (SQLException e) {
				// エラー発生時、独自のExceptionを発行
				throw new SwackException(ERR_DB_PROCESS, e);
			}

			return true;
		}
		
		/** Roomnemeの重複を確認するDAO **/
		public boolean checkRoom(String roomNane) throws SwackException {
			
			boolean flag=true;
			
			//  SQL
			String sql = "SELECT count(*) AS ROOMCOUNT FROM ROOMS R WHERE R.roomname = ?;";

			// Access DB
			try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
				// SQL作成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, roomNane);

				// SQLの実行
				ResultSet rs = pStmt.executeQuery();
				
				// 結果を詰め替え
				if (rs.next()) {
				 flag = rs.getBoolean("ROOMCOUNT");
				}
				
			} catch (SQLException e) {
				// エラー発生時、独自のExceptionを発行
				throw new SwackException(ERR_DB_PROCESS, e);
			}

			return flag;
		}
		
		/** JoinRoomの重複を確認するDAO **/
		public boolean checkJoinRoom(String roomId , String userId) throws SwackException {
			
			boolean flag=true;
			
			//  SQL
			String sql = "SELECT count(*) AS JOINROOMCOUNT FROM JOINROOM  WHERE roomId = ? AND userId = ?;";

			// Access DB
			try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
				// SQL作成
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, roomId);
				pStmt.setString(2, userId);

				// SQLの実行
				ResultSet rs = pStmt.executeQuery();
				
				// 結果を詰め替え
				if (rs.next()) {
				 flag = rs.getBoolean("JOINROOMCOUNT");
				 System.out.println(flag);
				}
				
			} catch (SQLException e) {
				// エラー発生時、独自のExceptionを発行
				throw new SwackException(ERR_DB_PROCESS, e);
			}

			return flag;
		}
}
