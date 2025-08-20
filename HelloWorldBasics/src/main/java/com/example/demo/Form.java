package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Form {
	
	String[] shoppingList = new String[100];
	int index = 0;
	
	@GetMapping("/register")
	public String getRegisterPage() {
		 return """ 
		 	 <form action= "/save" method = "get">
		 	<h2> Name:  <input type= "text" name = "fullname"  />   </h2> 
		 	<h2> Address:    <input type= "text" name = "address"  /> </h2>  
		 	     <input type= "submit"  value = "SUBMIT"  />
		 	     
		 	</form>
		 	   	   	  
		 		""";                
		
	}   
	

	
	@GetMapping("/save")
	public String saveRegisterPage(
			@RequestParam("fullname") String fullname,
			@RequestParam("address")  String address)  {
		 System.out.println("Name: " + fullname);
		 System.out.println("Address: " + address);

	return " <h1  > Success!! </h1>   <br> Full name: " + fullname + "<br> Address: " + address;
	}
	
	@GetMapping("/shopping/new")
	public String getAddShoppingItemPage() {
		return """
				 <form action = "/shopping/save" method = "get" >
				  <input type = "text" name = "item" /> 
				   <input type = "submit" value = "ADD ITEM" />
				   
				  </form>   
				""";
	}
	
	
	@GetMapping("/shopping/save")
	public String saveNewShoppingItem(
			@RequestParam("item") String item ) {
		
		System.out.println("item: " + item );
		shoppingList[index++] = item;
		
		return "<h3>Item Added!!</h3><br><a href='/shopping/list'>Go to Shopping List</a>";
	}
			
	
	@GetMapping("/shopping/list")
	public String getShowShoppingListPage() {
		String result = "<h2>Shopping List</h2><ul>";
		for(int i = 0; i < index; i++) {
			if(shoppingList[i] != null) {
				result += "<li>" + shoppingList[i] + 
				          " <a href='/shopping/delete?item=" + shoppingList[i] + "'>Delete</a></li>";
			}
		}
		result += "</ul><br><a href='/shopping/new'>Add More Items</a>";
		return result;
	}

	
	@GetMapping("/shopping/delete")
	public String deleteShoppingItem(
			@RequestParam("item") String itemToDelete) {
		
		boolean found = false;
		for (int i = 0; i < index; i++) {
			if (shoppingList[i] != null && shoppingList[i].equals(itemToDelete)) {
				found = true;
				// shift remaining items left
				for (int j = i; j < index - 1; j++) {
					shoppingList[j] = shoppingList[j + 1];
				}
				shoppingList[index - 1] = null;
				index--;
				break;
			}
		}
		
		if (found) {
			return "<h3>Item '" + itemToDelete + "' deleted successfully!</h3><br><a href='/shopping/list'>Back to List</a>";
		} else {
			return "<h3>Item not found!</h3><br><a href='/shopping/list'>Back to List</a>";
		}
	}
}
