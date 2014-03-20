package com.eyeofmidas.drakon;

import java.io.IOException;

import org.apache.sshd.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eyeofmidas.drakon.auth.DrakonPasswordAuthenticator;
import com.eyeofmidas.drakon.auth.DrakonPublickeyAuthenticator;
import com.eyeofmidas.drakon.commands.CommandFactory;

public class DrakonServer {
	private Logger logger;
	private SshServer server;

	public DrakonServer(SshServer inServer, Logger inLogger) {
		this.server = inServer;
		this.logger = inLogger;
	}

	public static SshServer getDefaultDrakonSshServer() {
		SshServer sshServer = SshServer.setUpDefaultServer();
		sshServer.setPort(5180);

		sshServer.setPasswordAuthenticator(new DrakonPasswordAuthenticator());
		sshServer.setPublickeyAuthenticator(new DrakonPublickeyAuthenticator());

		// String hostKey = System.getProperty("java.io.tmpdir") +
		// "/drakon.hostKey";
		String hostKey = "cert/drakon.keystore";

		sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(hostKey));
		sshServer.setShellFactory(new CommandFactory());

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
