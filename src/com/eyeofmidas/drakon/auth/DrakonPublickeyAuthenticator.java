package com.eyeofmidas.drakon.auth;

import java.security.PublicKey;

import org.apache.sshd.server.PublickeyAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class DrakonPublickeyAuthenticator implements PublickeyAuthenticator {

	@Override
	public boolean authenticate(String arg0, PublicKey arg1, ServerSession arg2) {
		return false;
	}

}
