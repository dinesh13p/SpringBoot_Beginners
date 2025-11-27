package com.example.demo;

public class ShoppingItem {
	
	private String item;
	private String user;
	
	ShoppingItem(String item, String user) {
		this.item = item;
		this.user = user;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
}