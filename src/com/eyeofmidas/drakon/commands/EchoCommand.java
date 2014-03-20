package com.eyeofmidas.drakon.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

public class EchoCommand implements Command, Runnable {

	private InputStream inputStream;
	private OutputStream outputStream;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setErrorStream(OutputStream arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setExitCallback(ExitCallback arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInputStream(InputStream in) {
		this.inputStream = in;
	}

	@Override
	public void setOutputStream(OutputStream out) {
		this.outputStream = out;

	}

	@Override
	public void start(Environment arg0) throws IOException {
		new Thread(this).start();

	}

	@Override
	public void run() {
		int read;
		try {
			while ((read = inputStream.read()) != -1) {
				outputStream.write(read);
				outputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
