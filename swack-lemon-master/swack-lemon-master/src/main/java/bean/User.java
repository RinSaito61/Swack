package bean;

/**
 * ユーザ情報を管理するBean
 * */
public class User {

	private String userId;
	private String userName;
	private String mailAddress;
	private String password;

	public User() {
		// for JSP
	}

	public User(String userId, String userName, String mailAddress, String password) {
		this.userId = userId;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", mailAddress=" + mailAddress + ", password="
				+ password + "]";
	}

}
