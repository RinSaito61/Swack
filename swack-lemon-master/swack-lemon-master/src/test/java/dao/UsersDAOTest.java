package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bean.User;
import exception.SwackException;

class UsersDAOTest {

	// @Test
	void testSelect() throws SwackException {
		UsersDAO usersDAO = new UsersDAO();

		User user = usersDAO.select("taro@swack.com", "swack0001");

		System.out.println("testSelect()");
		System.out.println(user);
	}

	// @Test
	void testExists() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		boolean flag = usersDAO.exists("swack0001");

		// 結果の確認
		System.out.println("testExists()");
		System.out.println(flag);
	}

	@Test
	void testSelectMaxUserId() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		String userId = usersDAO.selectMaxUserId();

		// 結果の確認
		System.out.println("testSelectMaxUserId()");
		System.out.println(userId);
	}

	// @Test
	void testInsert() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();
		User user = new User("U1000","aaa","aaa@swack.com","swack1000");

		// テスト実行
		usersDAO.insert(user);

		// 結果の確認(DBの直接確認)
		System.out.println("testSelectMaxUserId()");
	}

	// @Test
	void testUserLIst() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		List<User> userList = usersDAO.getUserList("U0000");

		// 結果の確認
		System.out.println("testUserLIst()");
		System.out.println(userList.toString());
	}

	@Test
	void testGetadditionUser() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		List<User> userList = usersDAO.getadditionUser("R0001");

		// 結果の確認
		System.out.println("testGetadditionUser()");
		System.out.println(userList.toString());
	}
	
	@Test
	void testGetUserIdList() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		ArrayList<String> roomIdList = usersDAO.getUserIdList();

		// 結果の確認
		System.out.println("testGetUserIdList()");
		System.out.println(roomIdList.toString());
	}
	
	//@Test
	void testUpdatePassword() throws SwackException {
		// 準備
		UsersDAO usersDAO = new UsersDAO();

		// テスト実行
		usersDAO.updatePassword("U0001","swack0010");

		// 結果の確認(DBの直接確認)
		System.out.println("testUpdatePassword()");
	}
	
	//@Test
		void testGetPassword() throws SwackException {
			// 準備
			UsersDAO usersDAO = new UsersDAO();

			// テスト実行
			String password =  usersDAO.getPassword("U0001");

			// 結果の確認(DBの直接確認)
			System.out.println("testGetPassword()");
			System.out.println(password);
		}

}
