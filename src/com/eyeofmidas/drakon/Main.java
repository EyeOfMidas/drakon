package com.eyeofmidas.drakon;

public class Main {

	public static void main(String[] args) {
		DrakonServer drakonServer = new DrakonServer(DrakonServer.getDefaultDrakonSshServer(), DrakonServer.getDefaultDrakonLogger());
		drakonServer.start();
	}
}
