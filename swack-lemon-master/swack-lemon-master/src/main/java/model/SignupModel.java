package model;

import java.util.ArrayList;

import bean.User;
import dao.RoomDAO;
import dao.UsersDAO;
import exception.SwackException;

/**
 * 新規登録処理を実行するModel
 */
public class SignupModel {
	
	/**ユーザが登録できるかの確認*/
	public boolean cheakUser(String mailAddress) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		//返却するフラグ
		boolean flag = true;
		flag = usersDAO.exists(mailAddress);
		return flag;
	}
	
	/**ユーザーIDの生成*/
	public String createUserId () throws SwackException {
		String newUserId = "";
		UsersDAO usersDAO = new UsersDAO();
		//最大のユーザIDの取得
		String userId = usersDAO.selectMaxUserId();
		// 振り分けるユーザIDの生成
		int giveId = Integer.parseInt(userId.replaceAll("[^0-9]", "")) + 1;
		newUserId = "U" + String.format("%04d", giveId);
		
		return newUserId;
	}
	
	/**ユーザの登録*/
	public User insertUser(String userId ,String name, String mailAddress , String password) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		//ユーザ情報の登録
		User user = new User(userId, name, mailAddress, password);
		usersDAO.insert(user);
		
		return user;
	}
	
	/**ダイレクトRoomの作成*/
	public  void InsertDirected(String roomId  ,String roomName, String createdUserId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		//Room情報の登録
		roomDAO.setRoom(roomId, roomName, createdUserId, true, true);
		return ;
	}
	
	/**UserIdのListの作成*/
	public  ArrayList<String> CreateUserIdList() throws SwackException {
		//User情報の取得
		UsersDAO userDAO = new UsersDAO();
		ArrayList<String> UserIdList = userDAO.getUserIdList();
		return UserIdList;
	}
}
