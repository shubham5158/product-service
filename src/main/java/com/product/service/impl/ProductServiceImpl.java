package com.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	public static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	private byte[] imageByte;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public Product createProduct(Product product) {
		if (product.getImageUrl() != null) {
			

			// Save the modified image
			product.setImageUrl(imageByte);
		}
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Optional<Product> existingProduct = productRepository.findById(id);
		if (existingProduct.isPresent()) {
			Product product2 = existingProduct.get();

			if (product.getName() != null) {
				product2.setName(product.getName());
			}

			if (product.getPrice() != null) {
				product2.setPrice(product.getPrice());
			}

			if (product.getDiscription() != null) {
				product2.setDiscription(product.getDiscription());
			}
			Product saveProduct = productRepository.save(product2);
			return saveProduct;
		} else {
			return product;
		}

	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}
