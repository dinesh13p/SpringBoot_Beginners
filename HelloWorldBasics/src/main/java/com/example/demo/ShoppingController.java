package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ShoppingController {
    List<ShoppingItem> shoppingList = new ArrayList<>();

    /**
     * Display the shopping list for a specific user
     */
    @GetMapping("/shopping/list/{user}")
    public String getShowShoppingListPage(@PathVariable("user") String user) {
        String result = "<h1>Hello, " + user + "!</h1>";
        result = result + "<p>Your shopping list:</p>";
        
        // Check if user has any items
        boolean userHasItems = false;
        for (ShoppingItem item : shoppingList) {
            if (item.getUser().equals(user)) {
                userHasItems = true;
                break;
            }
        }
        
        if (!userHasItems) {
            result = result + "<p><b>No items</b></p>";
        } else {
            result = result + "<ul>"; // begin the list
            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).getUser().equals(user)) {
                    result = result + "<li><span>" + shoppingList.get(i).getItem() + "</span>"
                            + "<a href=\"/shopping/delete/" + i + "/" + user + "\"> Remove </a>" + "</li>";
                }
            }
            result = result + "</ul>"; // end the list
        }
        
        // Form to add new item
        result = result + """
                <form action="/shopping/save" method="get">
                <input type="text" placeholder="Item" name="item" required />
                <input type="hidden" name="user" """ + "value=\"" + user + "\" />" + """
                <input type="submit" value="ADD ITEM" />
                </form>
                """;
        
        System.out.println(result); // print raw HTML
        return result;
    }

    @GetMapping("/shopping/save")
    public String saveNewShoppingItem(@RequestParam("item") String item, @RequestParam("user") String user,
            HttpServletResponse resp) throws IOException {
        // Validate input
        if (item != null && !item.trim().isEmpty()) {
            shoppingList.add(new ShoppingItem(item.trim(), user));
        }
        resp.sendRedirect("/shopping/list/" + user); // browser goes to this url
        return "Item added!";
    }

    // Delete an item from the shopping list
    @GetMapping("/shopping/delete/{index}/{user}")
    public String deleteShoppingItem(@PathVariable("index") int index, @PathVariable("user") String user, 
            HttpServletResponse resp) throws IOException {
        // Validate index and ensure the item belongs to the user
        if (index >= 0 && index < shoppingList.size() && 
            shoppingList.get(index).getUser().equals(user)) {
            shoppingList.remove(index);
        }
        resp.sendRedirect("/shopping/list/" + user); // browser goes to correct user's list
        return "Item deleted!";
    }
    
    // Optional: Add a method to view all items (for debugging)
    @GetMapping("/shopping/all")
    public String getAllItems() {
        if (shoppingList.isEmpty()) {
            return "<h1>No items in any shopping list</h1>";
        }
        
        String result = "<h1>All Shopping Items</h1><ul>";
        for (int i = 0; i < shoppingList.size(); i++) {
            ShoppingItem item = shoppingList.get(i);
            result += "<li>Index " + i + ": " + item.getItem() + " (User: " + item.getUser() + ")</li>";
        }
        result += "</ul>";
        return result;
    }
}