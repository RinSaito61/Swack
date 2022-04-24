package model;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

/**
 * ログイン機能を実行するModel
 */
public class LoginModel {

	/**入力されたログイン情報を確かめる処理*/
	public User checkLogin(String mailAddress, String password) throws SwackException {
		System.out.println("[checkLogin] " + mailAddress + " " + password);

		UsersDAO usersDAO = new UsersDAO();

		User user = usersDAO.select(mailAddress, password);

		return user;

	}

}
