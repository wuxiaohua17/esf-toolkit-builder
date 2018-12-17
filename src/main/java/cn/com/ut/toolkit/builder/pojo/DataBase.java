package cn.com.ut.toolkit.builder.pojo;

public class DataBase {

	public DataBase() {

	}

	private String db;
	private String username;
	private String password;
	private String url;

	public String getDb() {

		return db;
	}

	public void setDb(String db) {

		this.db = db;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		if (url.indexOf("?") == -1) {
			this.url = url + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
		} else {
			this.url = url;
		}
	}

}
