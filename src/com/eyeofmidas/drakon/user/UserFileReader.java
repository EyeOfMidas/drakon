package com.eyeofmidas.drakon.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;

public class UserFileReader {

	public ArrayList<User> read(String filename) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			FileInputStream inputStream = new FileInputStream(filename);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			JsonReader reader = new JsonReader(inputStreamReader);
			reader.beginArray();
			while (reader.hasNext()) {
				users.add(readUserData(reader));
			}
			reader.endArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	private User readUserData(JsonReader reader) throws IOException {
		User currentUser = new User();
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (key.equals("id")) {
				currentUser.setId(reader.nextInt());
			} else if (key.equals("name")) {
				currentUser.setName(reader.nextString());
			} else if (key.equals("password")) {
				currentUser.setPassword(reader.nextString());
			} else if (key.equals("email")) {
				currentUser.setEmail(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return currentUser;
	}
}
