package com.product.service;

import java.util.List;

import com.product.entity.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Product getProductById(Long id);

	Product createProduct(Product product);

	Product updateProduct(Long id, Product product);

	void deleteProduct(Long id);

}
