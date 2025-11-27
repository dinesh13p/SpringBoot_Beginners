package com.example.demo.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository repository;

	@GetMapping("/products")
	public String getProducts(Model model) {
		// SELECT * FROM products
		model.addAttribute("products", repository.findAll());
		model.addAttribute("forUpdate", false);
		model.addAttribute("product", new Product());
		return "products.html";
	}
	
	@PostMapping("/product")
	public String saveNewProduct(Product product) {
		// upsert object in database
		
		// TODO: check if productRepository already contains a product with given name
//		if(repository.existsByName(product.getName())) {
//			model.addAttribute("error", "Product with given name already exists");
//		}
		
		//TODO: should check for validation errors
//		if(valid) {
//			// save & redirect
//		} else {
//			// send error message on the page
//		}
//		
		repository.save(product);  // Actually saved in database
		// INSERT INTO product (name, price) VALUES ('Apple M2', 240000);
		return "redirect:/products";
	}
	
	
	/**
	 * Mapping to display edit form
	 */
	@GetMapping("/products/edit/{id}")
	public String getProductEditPage(@PathVariable("id") int productID, Model model) {
		// productID = 7
		// SELECT * FROM product WHERE id = 7
		// This query returns a row and Hibernate encapsulates that row into Product object
		// Product(7, "Apple M2", 240000)
		model.addAttribute("product", repository.findById(productID).get());
		model.addAttribute("products", repository.findAll());
		model.addAttribute("forUpdate", true);
		return "products.html";
	}
	
	@PostMapping("/products/delete")
	public String deleteProductByID(@RequestParam("id") int productID) {
		repository.deleteById(productID);
		return "redirect:/products";
	}
	
	@GetMapping("/products/search")
	public String getProductSearchResultPage(@RequestParam("query") String searchQuery,
			Model model) {
		List<Product> products = repository.findByNameContainingIgnoreCase(searchQuery);
		model.addAttribute("products", products);
		model.addAttribute("forUpdate", false);
		model.addAttribute("product", new Product());
		return "products.html";
	}
	
}
