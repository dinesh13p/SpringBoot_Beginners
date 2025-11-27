package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ChatController {

	List<Chat> chats = new ArrayList<>();

	@GetMapping("/chat/users")
	public String showAllUsersPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// check if user is logged in
		// if not, redirect to /login
		LoginInfo user = UsefulMethods.getLoggedInUser(request);

		if (user == null) {
			response.sendRedirect("/login");
			return "";
		}

		String userList = "";
		for (LoginInfo info : UserRegistration.allLoggedInUsers) {
			userList = userList + String.format("<li>%s - <a href=\"/chat/room/%s\">%s</a></li>", info.getFullName(),
					info.getUsername(), info.getUsername());
		}

		return "<h3>Available users:</h3>" + "<ul>" + userList + "</ul>";

	}

	@GetMapping("/chat/room/{receiver}")
	public String showChatRoomPage(@PathVariable("receiver") String receiver, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// check if user is logged in
		// if not, redirect to /login
		LoginInfo user = UsefulMethods.getLoggedInUser(request);

		if (user == null) {
			response.sendRedirect("/login");
			return "";
		}

		// create a list of <p> tags containing your received chats;

		// show only the messages related to the receiver
		// i.e. sent by the receiver OR received by the receiver

		String myChats = "";
		for (Chat chat : chats) {
			boolean isReceived = chat.getFromUsername().equals(receiver)
					&& chat.getToUsername().equals(user.getUsername());
			boolean isSent = chat.getToUsername().equals(receiver)
					&& chat.getFromUsername().equals(user.getUsername());
			
			if(isReceived) {
				myChats += "<p style=\"color: red;\">" + chat.getMessage() + "</p>";
			} else if(isSent) {
				myChats += "<p>" + chat.getMessage() + "</p>";
			}
			
		}

		String hiddenInput = "<input type=\"hidden\" name=\"receiver\" value=\"" + receiver + "\" /> <br>";

		return "<h1>" + receiver + "</h1>" + myChats + """
				<form action="/chat/send" method="get">
					<input type="text" name="message" placeholder="Message" /> <br>
				""" + hiddenInput + """
					<input type="submit" value="Send" />
				</form>
				<a href="javascript:window.location.reload();">Refresh</a>
				""";
	}

	@GetMapping("/chat/send")
	public String sendChatMessage(@RequestParam("receiver") String receiverUsername,
			@RequestParam("message") String message, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// TODO: use POST method for data privacy

		// check if sender is logged in
		// if not redirect to /login
		LoginInfo user = UsefulMethods.getLoggedInUser(request);
		if (user == null) {
			response.sendRedirect("/login");
			return "";
		}

		// Check if receiversUsername actually exists in the system
		// and send error message if it doesn't
		boolean hasFoundReceiver = false;
		for (LoginInfo e : UserRegistration.allLoggedInUsers) {
			if (e.getUsername().equals(receiverUsername)) {
				hasFoundReceiver = true;
				break;
			}
		}
		if (hasFoundReceiver == false) {
			return "Sorry, could not find given receiver's username!";
		}

		// create a Chat object with following information:
		// fromUsername
		// toUsername
		// message
		Chat chat = new Chat(user.getUsername(), receiverUsername, message);

		// append that Chat object to Chat array database
		chats.add(chat);

		// redirect to /chat/room
		response.sendRedirect("/chat/room/" + receiverUsername);

		return "DEBUG: " + receiverUsername + " <- " + message;
	}

}
