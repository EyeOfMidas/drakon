package com.eyeofmidas.drakon.auth;

import java.util.ArrayList;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import com.eyeofmidas.drakon.user.User;
import com.eyeofmidas.drakon.user.UserFileReader;

public class DrakonPasswordAuthenticator implements PasswordAuthenticator {

	@Override
	public boolean authenticate(String username, String password, ServerSession session) {
		UserFileReader userFileReader = new UserFileReader();
		ArrayList<User> users = userFileReader.read("data/users.json");
		for(User user : users) {
			if(user.isAuthUser(username, password)){
				return true;
			}
		}
		return false;
	}

}
