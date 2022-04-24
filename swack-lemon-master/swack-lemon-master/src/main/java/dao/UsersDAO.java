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

import bean.User;
import exception.SwackException;

/**
 * ユーザ機能に関するDBアクセスを行う.
 */
public class UsersDAO {

	/** ログインするユーザの検索をするDAO */
	public User select(String mailAddress, String password) throws SwackException {
		// SQL
		String sql = "SELECT USERID, USERNAME FROM USERS WHERE MAILADDRESS = ? AND PASSWORD = ?";

		User user = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {

			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);
			pStmt.setString(2, password);

			// SQL実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			if (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				user = new User(userId, userName, mailAddress, "********");
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		// 結果の返却（取得できなかった場合、nullが返却される）
		return user;
	}

	/** 同じメールアドレスのユーザーが存在するか確認するDAO */
	public boolean exists(String mailAddress) throws SwackException {
		// SQL
		String sql = "SELECT count(*) AS myCnt FROM users WHERE mailAddress = ?";
		// 呼び出し先に返す変数
		boolean flag = false;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果の取得
			if (rs.next()) {
				flag = rs.getBoolean("myCnt");
			}
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return flag;

	}

	/** 値が一番大きいユーザIDを取得するDAO */
	public String selectMaxUserId() throws SwackException {
		// TODO SQL
		String sql = "SELECT max(userid) AS max_userid FROM users;";

		String maxUserId = null;

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			//結果の取得
			rs.next();
			maxUserId = rs.getString("max_userid");
		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return maxUserId;
	}

	/** ユーザーの登録を行うSQL */
	public boolean insert(User user) throws SwackException {
		// SQL
		String sql = "INSERT INTO users VALUES (? , ? , ? , ?);";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getUserName());
			pStmt.setString(3, user.getMailAddress());
			pStmt.setString(4, user.getPassword());

			// SQLの実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return true;

	}

	/**ログインしているユーザー以外のユーザーリスト作成するDAO*/
	public List<User> getUserList(String nowUserId) throws SwackException {
		// SQL
		String sql = "SELECT USERID, USERNAME  FROM USERS WHERE  userid <>?";

		ArrayList<User> userlist = new ArrayList<User>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, nowUserId);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");

				User user = new User(userId, userName, "", "");
				userlist.add(user);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userlist;

	}

	/**RoomIDからそのRoomに入っていないUserList作成するDAO*/
	public List<User> getadditionUser(String nowRoomId) throws SwackException {
		// SQL
		String sql = "SELECT U1.userid,U1.userName FROM USERS U1 WHERE U1.userid<>'U0000' AND U1.userid NOT IN (SELECT U2.userid FROM JOINROOM R JOIN USERS U2 ON R.USERID = U2.USERID WHERE R.ROOMID = ?)";

		ArrayList<User> userlist = new ArrayList<User>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, nowRoomId);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");

				User user = new User(userId, userName, "", "");
				userlist.add(user);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userlist;

	}

	/**RoomIDList作成するDAO*/
	public ArrayList<String> getUserIdList() throws SwackException {
		// SQL
		String sql = "SELECT userid FROM USERS U1 WHERE userid <>'U0000' ;";

		ArrayList<String> userIdlist = new ArrayList<String>();

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果を詰め替え
			while (rs.next()) {
				String userId = rs.getString("USERID");
				userIdlist.add(userId);
			}

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userIdlist;

	}

	/**ユーザーのパスワードを変更するDAO*/
	public void updatePassword(String userId, String password) throws SwackException {
		// SQL
		String sql = "UPDATE users SET password = ?  WHERE userid = ? ;";

		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, password);
			pStmt.setString(2, userId);

			// SQLの実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**ユーザーのパスワードを取得するDAO*/
	public String getPassword(String userId) throws SwackException {
		// SQL
		String sql = "SELECT password FROM users WHERE userid = ? ;";

		String password;
		
		// Access DB
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			// SQL作成
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			// SQLの実行
			ResultSet rs = pStmt.executeQuery();

			// 結果の取得
			rs.next();
			password = rs.getString("PASSWORD");

		} catch (SQLException e) {
			// エラー発生時、独自のExceptionを発行
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return password;
	}

}
