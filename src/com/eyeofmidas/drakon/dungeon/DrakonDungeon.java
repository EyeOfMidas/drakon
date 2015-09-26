package com.eyeofmidas.drakon.dungeon;

public class DrakonDungeon {

	public static DrakonDungeon getInstance() {
		return new DrakonDungeon();
	}

	private String message;
	private boolean isActive = true;
	private boolean hasUpdate = false;

	public String output() {
		hasUpdate = false;
		return message;
	}

	public void takeAction(String command) {
		if (command.equals("exit") || command.equals("quit")) {
			this.exitRequest();
		} else {
			message = "\r\nYou said: \"" + command + "\"\r\n";
			hasUpdate = true;
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void exitRequest() {
		isActive = false;
		message = "\r\nGoodbye!\r\n";
		hasUpdate = true;

	}

	public void startup() {
		isActive = true;
		message = "Welcome to Drakon!\r\n";
		hasUpdate = true;
	}

	public boolean hasUpdate() {
		return hasUpdate;
	}

}
