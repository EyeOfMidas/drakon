package com.eyeofmidas.drakon;

import java.io.IOException;

import org.apache.sshd.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eyeofmidas.drakon.auth.DrakonPasswordAuthenticator;
import com.eyeofmidas.drakon.auth.DrakonPublickeyAuthenticator;
import com.eyeofmidas.drakon.commands.DrakonShellFactory;

public class DrakonServer {
	private Logger logger;
	private SshServer server;

	public DrakonServer() {
		this.server = DrakonServer.getDefaultDrakonSshServer();
		this.logger = DrakonServer.getDefaultDrakonLogger();
	}

	public static SshServer getDefaultDrakonSshServer() {
		String hostKey = "cert/drakon.keystore";
		int port = 5180;
		SshServer sshServer = SshServer.setUpDefaultServer();
		sshServer.setPort(port);
		sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostKey));
		sshServer.setShellFactory(new DrakonShellFactory());
		sshServer.setPasswordAuthenticator(new DrakonPasswordAuthenticator());
		sshServer.setPublickeyAuthenticator(new DrakonPublickeyAuthenticator());
		return sshServer;

	}

	public static Logger getDefaultDrakonLogger() {
		return LoggerFactory.getLogger(Main.class);

	}

	public void start() {
		logger.info("drakon start");
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		logger.info("drakon shutdown");
		try {
			server.stop();
		} catch (InterruptedException e) {
			logger.error("Unable to shutdown cleanly");
			e.printStackTrace();
		}
	}

}
