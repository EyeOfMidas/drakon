package com.eyeofmidas.drakon.commands;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;

public class CommandFactory implements Factory<Command> {

	@Override
	public Command create() {
		return new EchoCommand();
	}

}
