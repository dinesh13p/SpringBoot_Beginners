package com.example.demo;

import java.security.SecureRandom;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class UsefulMethods {

	static String COOKIE_NAME = "SECUREID";

	static String sampleSpace = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	static int SIZE = sampleSpace.length();

	public static String getRandomString(int len) {

		SecureRandom random = new SecureRandom();

		String ans = "";

		for (int i = 1; i <= len; i++) {
			int index = random.nextInt(SIZE);
			ans = ans + sampleSpace.charAt(index); // sampleSpace[index]
		}

		return ans;
	}

	public static LoginInfo getLoggedInUser(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		boolean userLoggedIn = false;
		String token = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("SECUREID")) {
					userLoggedIn = true;
					token = cookie.getValue();
					break;
				}
			}
		}

		LoginInfo info = null;
		if (userLoggedIn) {
			for (LoginInfo loginInfo : UserRegistration.allLoggedInUsers) {
				if (loginInfo.getToken().equals(token)) {
					info = loginInfo;
					break;
				}
			}
		}

		return info;

	}

}
