package com.eyeofmidas.drakon.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import com.eyeofmidas.drakon.dungeon.DrakonDungeon;

public class DungeonShell implements Command, Runnable {

	private InputStream inputStream;
	private OutputStream outputStream;
	private ExitCallback exitCallback;
	private Thread thread;
	private StringBuffer inputBuffer;
	private PrintWriter printOutputStream;
	private String command;
	private DrakonDungeon dungeon;

	public DungeonShell() {
		inputBuffer = new StringBuffer();
	}

	@Override
	public void setInputStream(InputStream in) {
		inputStream = in;
	}

	@Override
	public void setOutputStream(OutputStream out) {
		if (printOutputStream != null) {
			printOutputStream.close();
		}
		outputStream = out;
		printOutputStream = new PrintWriter(outputStream);

	}

	@Override
	public void setErrorStream(OutputStream err) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExitCallback(ExitCallback callback) {
		exitCallback = callback;

	}

	@Override
	public void start(Environment env) throws IOException {
		thread = new Thread(this, "DungeonShell");
		dungeon = DrakonDungeon.getInstance();
		thread.start();
	}

	@Override
	public void run() {
		dungeon.startup();
		char read = '\0';
		try {
			while (dungeon.isActive()) {
				if (read == '\n' || read == '\r') {
					command = inputBuffer.toString();
					dungeon.takeAction(command);
					inputBuffer = new StringBuffer();
				} else if (read == '\u007f') {
					printOutputStream.print("\b ");
					if (inputBuffer.length() >= 1) {
						inputBuffer.deleteCharAt(inputBuffer.length() - 1);
					}
				} else if (read == '\u0003') {
					dungeon.exitRequest();
				} else {
					inputBuffer.append(read);
				}
				printOutputStream.print("\r" + inputBuffer.toString());
				if (dungeon.hasUpdate()) {
					printOutputStream.print(dungeon.output());
				}
				printOutputStream.flush();
				if (!dungeon.isActive()) {
					return;
				}
				read = (char) inputStream.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			exitCallback.onExit(0);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
