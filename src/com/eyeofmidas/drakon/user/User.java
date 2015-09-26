package com.eyeofmidas.drakon.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.sshd.common.util.Base64;

public class User {

	private int id;
	private String name;
	private String password;
	private String email;
	private String publicKey;

	public void setId(int nextInt) {
		id = nextInt;
	}

	public void setName(String nextString) {
		name = nextString;
	}

	public void setPassword(String nextString) {
		password = nextString;
	}

	public void setEmail(String nextString) {
		email = nextString;
	}

	public void setPublicKey(String nextString) {
		publicKey = nextString;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAuthUser(String username, String userPassword) {
		if (!getName().equals(username)) {
			return false;
		}
		userPassword = DigestUtils.sha1Hex(userPassword);
		return password.equals(userPassword);
	}

	public boolean isAuthUser(String username, PublicKey key) {
		if (!getName().equals(username)) {
			return false;
		}
		String[] publicKeyComponents = publicKey.split(" ");
		if (key instanceof RSAPublicKey && publicKeyComponents[0].equals("ssh-rsa")) {
			String s1 = new String(encode((RSAPublicKey) key));

			String s2 = new String(Base64.decodeBase64(publicKeyComponents[1].getBytes()));
			return s1.equals(s2); // Returns true if the key matches our known
									// key, this allows auth to proceed.
		}
		return false;
	}

	// Converts a Java RSA PK to SSH2 Format.
	public static byte[] encode(RSAPublicKey key) {
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			byte[] name = "ssh-rsa".getBytes("US-ASCII");
			write(name, buf);
			write(key.getPublicExponent().toByteArray(), buf);
			write(key.getModulus().toByteArray(), buf);
			return buf.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void write(byte[] str, OutputStream os) throws IOException {
		for (int shift = 24; shift >= 0; shift -= 8)
			os.write((str.length >>> shift) & 0xFF);
		os.write(str);
	}

}
