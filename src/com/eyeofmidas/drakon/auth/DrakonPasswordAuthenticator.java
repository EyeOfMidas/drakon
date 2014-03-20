package com.eyeofmidas.drakon.auth;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class DrakonPasswordAuthenticator implements PasswordAuthenticator {

	@Override
	public boolean authenticate(String username, String password, ServerSession session) {
		if (username.equals("midas") && password.equals("midas")) {
			return true;
		}
		return false;
	}

}
