package com.example.demo.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	Product findByName(String productName);

	boolean existsByName(String name);

	List<Product> findByNameContainingIgnoreCase(String searchQuery);

}
