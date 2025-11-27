package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlBasedController {

	@GetMapping("/homepages")
	public String getHomePage(Model model) {
		model.addAttribute("name", "Nepathya");
		model.addAttribute("isClosed", true);
		List<String> fruitList = Arrays.asList("Apple", "Orange", "Raspberry");
		model.addAttribute("fruits", fruitList);
		return "homepage.html";
	}
	
}
