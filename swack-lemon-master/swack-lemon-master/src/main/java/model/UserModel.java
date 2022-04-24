package model;

import dao.UsersDAO;
import exception.SwackException;

/**
 * ユーザー情報に関するModel
 */
public class UserModel {

	/**パスワード変更*/
		public void updatePassword (String userId, String password) throws SwackException {
			UsersDAO usersDAO = new UsersDAO();
			//実行
			usersDAO.updatePassword(userId, password);
		}
		
	/**パスワードの取得*/
		public String getPassword (String userId) throws SwackException {
			UsersDAO usersDAO = new UsersDAO();
			//実行
			String password = usersDAO.getPassword(userId);
			return password;
		}
}
