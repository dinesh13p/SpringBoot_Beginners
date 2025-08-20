package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Form {

	@GetMapping("/register")
	public String getRegisterPage() {
		return """
				<form action="/save" method="get">
				<input type="text" name="fullname" />
				<input type="text" name="address" />
				<input type="submit" value="SUBMIT" />
				</form>""";
	}

	@GetMapping("/save")
	public String saveRegisterPage(@RequestParam("fullname") String fullname, @RequestParam("address") String address) {
		System.out.println("Name: " + fullname);
		System.out.println("Address: " + address);
		return "Form submitted!";
	}


}