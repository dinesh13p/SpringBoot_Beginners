package com.example.demo;

import java.util.Date;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	int count = 0;
	int num;
	

	@GetMapping("/greet")
	public String greet() {
		return "Hello World";

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
		return   """
				  <p style= "font-size: 144px;">
				""" + roll[val] + "</p>" + """ 
						    
				   <a href = "/dice"> Roll again  </a>  """ ;
	}
	 
     @GetMapping("/date")
	 public String datetime() {
	     Date date = new Date();
		 return "Current time: " + date;
	 }
     
     @GetMapping("/square/{num}")
     public String square(@PathVariable("num") int n) {
    	  return n + "^2 = " + n * n;
     }
     
     
     @GetMapping("/multiply/{num}/{num2}")
     public String multiply(@PathVariable("num") int n, @PathVariable("num2") int x) {
    	 
    	 return n + " x " +  x + " = " + n * x;
     }
     
     @GetMapping("/")
     public String home() {
           return """ 
           		       <a href = "/greet" > This is Greet.  </a> 
           		              """; 
        }       
     
     
     
     
     
     }