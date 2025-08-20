package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class Multiple_Number {
	@GetMapping("/multiple/{limit}")
    
     public String multiples(@PathVariable("limit") int limit) {
    	
    	String result = "";
    	
    	int fontSize = 10;
    	for(int i = 7; i <= limit; i= i+ 7) {
    	
    		
    		result = result + "<p style=\"font-size:"  
    						+ fontSize + "\">" + i + "</p>";
    		
    		fontSize += 2;
    	}
    	
    	
    	return result;
    }
}