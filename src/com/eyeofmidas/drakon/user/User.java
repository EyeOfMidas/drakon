package com.eyeofmidas.drakon.user;

import org.apache.commons.codec.digest.DigestUtils;

public class User {

	private int id;
	private String name;
	private String password;
	private String email;

	public void setId(int nextInt) {
		id = nextInt;
	}

	public void setName(String nextString) {
		name = nextString;
	}

	public void setPassword(String nextString) {
		password = nextString;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAuthUser(String username, String userPassword) {
		userPassword = DigestUtils.sha1Hex(userPassword);
		return (name.equals(username) && password.equals(userPassword));
	}

	public void setEmail(String nextString) {
		email = nextString;
	}

}
