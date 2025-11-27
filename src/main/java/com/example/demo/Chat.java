package com.example.demo;

public class Chat {

	private String fromUsername;
	private String toUsername;
	private String message;

	public Chat(String fromUsername, String toUsername, String message) {
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
		this.message = message;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
