package com.eyeofmidas.drakon.auth;

import java.security.PublicKey;
import java.util.ArrayList;

import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import com.eyeofmidas.drakon.user.User;
import com.eyeofmidas.drakon.user.UserFileReader;

public class DrakonPublickeyAuthenticator implements PublickeyAuthenticator {

	@Override
	public boolean authenticate(String username, PublicKey key, ServerSession session) {
		UserFileReader userFileReader = new UserFileReader();
		ArrayList<User> users = userFileReader.read("data/users.json");
		for(User user : users) {
			if(user.isAuthUser(username, key)){
				return true;
			}
		}
		return false;
	}

}
