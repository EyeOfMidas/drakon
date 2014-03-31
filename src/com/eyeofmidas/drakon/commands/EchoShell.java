package com.eyeofmidas.drakon.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

public class EchoShell implements Command, Runnable {

	private InputStream inputStream;
	private OutputStream outputStream;
	private ExitCallback exitCallback;
	private Thread thread;
	private StringBuffer inputBuffer;
	private PrintWriter printOutputStream;
	private String command;

	public EchoShell() {
		inputBuffer = new StringBuffer();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setErrorStream(OutputStream out) {

	}

	@Override
	public void setExitCallback(ExitCallback callback) {
		exitCallback = callback;

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
	public void start(Environment env) throws IOException {
		thread = new Thread(this, "EchoShell");
		thread.start();
	}

	@Override
	public void run() {
		char read;
		try {
			while ((read = (char) inputStream.read()) != -1) {
				if (read == '\n' || read == '\r') {
					command = inputBuffer.toString();
					printOutputStream.print("\r\nServer: " + command + "\r\n");
					inputBuffer = new StringBuffer();
				} else if (read == '\u007f') {
					printOutputStream.print("\b \b");
					if (inputBuffer.length() >= 1) {
						inputBuffer.deleteCharAt(inputBuffer.length() - 1);
					}
				} else if (read == '\u0003') {
					return;
				} else {
					inputBuffer.append(read);
					printOutputStream.print(read);
				}
				printOutputStream.flush();
				if ("quit".equals(command)) {
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			exitCallback.onExit(0);
		}

	}
}
