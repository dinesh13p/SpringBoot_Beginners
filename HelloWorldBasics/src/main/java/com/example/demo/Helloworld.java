package com.example.demo;

import java.util.Date;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Helloworld {

	int count = 0;

	@GetMapping("/greet")
	public String greet() {
		return "Hello World!";
	}

	@GetMapping("/date")
	public String datetime() {
		Date date = new Date();
		return "Current time: " + date;
	}

	@GetMapping("/count")
	public String pageVisits() {
		count++;
		return "Page visited " + count + " times.";
	}

	@GetMapping("/dice")
	public String dice() {
		int val = (new Random()).nextInt(6);
		String[] roll = { "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX" };

		return """
				<p style="font-size: 144px;">
				""" + roll[val] + "</p>" + """
						<a href="/dice">Roll again!</a>
						""";
	}
	
//	/multiply/12/15
	@GetMapping("/multiply/{num}/{num2}")
	public String square(@PathVariable("num") int n, @PathVariable("num2") int x) {
		// Here n = 12, x = 15
		return n + " X " + x + " = " + n * x;
	}
	
	
	@GetMapping("/")
	public String getHomePage() {
		return """
				<a href="/greet">Greet Page</a> <br>
				<a href="/date">View Date & Time</a> <br>
				<a href="/dice">Roll a dice</a> <br>
				""";
	}
	
	@GetMapping("/para/{count}")
	public String paragraph(@PathVariable("count") int count) {
		String[] lines = {
				"An apple a day keeps the doctor away.",
				"A quick brown fox jumps over the very lazy dog.",
				"Spring Boot servers are popular in finance and health sectors.",
				"We can write our own web servers in C as well.",
				"NVIDIA is the most valuable company in the world surpassing Aramco."
		};
		
		Random r = new Random();
		String out = "";
		for(int i = 1; i <= count; i ++) {
			int index = r.nextInt(lines.length);
			out = out + lines[index];
			out = out + " ";
		}
		
		return out;
		
	}
	
	@GetMapping("/multiples/{limit}")
	public String multiples(@PathVariable("limit") int limit) {
		
		String result = "";
		
		int fontSize = 10;
		for(int i = 7; i <= limit; i = i + 7) {
			result = result + "<p style=\"font-size:" 
					+ fontSize + "\">" + i + "</p>";
			fontSize += 2;
		}
		
		return result;
	}
	
}