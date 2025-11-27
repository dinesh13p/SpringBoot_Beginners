package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class Food {
	private String name;
	private boolean isFruit;

	public Food(String name, boolean isFruit) {
		this.name = name;
		this.isFruit = isFruit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFruit() {
		return isFruit;
	}

	public void setFruit(boolean isFruit) {
		this.isFruit = isFruit;
	}

}

@Controller
public class UserRegistration {
	
	// this array is for demo only
	List<Food> foods = Arrays.asList(
			new Food("Orange", true),
			new Food("Potato", false),
			new Food("Cauli", false),
			new Food("Grapes", true)
			);
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		model.addAttribute("name", "Nepathya College");
		model.addAttribute("foods", foods);
		return "login.html";
	}

	public static List<LoginInfo> allLoggedInUsers = new ArrayList<>();

	@GetMapping("/")
	public String getHomePage(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		// check if user has token in their cookies
		LoginInfo info = UsefulMethods.getLoggedInUser(request);
		if (info != null) {
			// user's cookie has SECUREID (which was probably given by the server)
			model.addAttribute("user", info);
			return "home.html";
		} else {
			return "login.html";
		}
	}

//	@GetMapping("/profile")
//	public String getProfilePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//		LoginInfo info = UsefulMethods.getLoggedInUser(request);
//
//		if (info != null) {
//			// user's cookie has SECUREID (which was probably given by the server)
//			return "<h2>Hello, " + info.getUsername() + "!</h2>" + """
//					<form action="/profile/save" method="get">
//					<input type="text" placeholder="Full Name" name="fullName" />
//					<input type="text" placeholder="Email" name="email" />
//					<input type="submit" value="SAVE" />
//					</form>""";
//		} else {
//			response.sendRedirect("/login");
//		}
//		return "";
//	}

//	@GetMapping("/profile/save")
//	public String saveProfile(@RequestParam("fullName") String fullName, @RequestParam("email") String email,
//			HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//		LoginInfo info = UsefulMethods.getLoggedInUser(request);
//
//		if (info != null) {
//			// actually save the fullname & email in database
//			info.setFullName(fullName);
//			info.setEmail(email);
//
//			response.sendRedirect("/");
//
//		} else {
//			response.sendRedirect("/login");
//		}
//		return "";
//	}

	@PostMapping("/login")
	public String saveLoginPage(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletResponse response) throws IOException {
		// This method generates, saves & provides new token to the user with given
		// username

		// TASK: if user is already in the system, return without creating a new user
		// TASK: ask for password as well to really secure the user

		LoginInfo loginInfo = null;

		// 1. Generate token
		String token = UsefulMethods.getRandomString(20);

		// Check if username already exists in the database
		for (LoginInfo info : allLoggedInUsers) {
			if (info.getUsername().equals(username)) {
				if (info.getPassword().equals(password)) {
					loginInfo = info;
					loginInfo.setToken(token);
					break;
				} else {
					return """
							<p style="color: red;">
							Invalid credentials!</p>""";
				}
			}
		}

		// If username is not already in the database, create new user as follows
		if (loginInfo == null) {
			// 2. Save token to database along with username for a NEW user
			loginInfo = new LoginInfo(username, token);
			loginInfo.setPassword(password);
			allLoggedInUsers.add(loginInfo);
		}

		// 3. send token to user/client/browser
		Cookie cookie = new Cookie(UsefulMethods.COOKIE_NAME, token);
		cookie.setPath("/");
		response.addCookie(cookie);

//		response.sendRedirect("/");

		return "home.html";
	}

}
