package com.product.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.product.entity.Product;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	public static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {

		Product product = productService.getProductById(id);
		if (product != null && product.getImageUrl() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type for your images

			return new  ResponseEntity<Product>(headers, HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
//		Product product = productService.getProductById(id);
//		return ResponseEntity.ok(product);
//	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
			@RequestParam("image") MultipartFile imageFile) throws IOException {

		try {
			if (!imageFile.isEmpty()) {
				byte[] imageBytes = imageFile.getBytes();
				product.setImageUrl(imageBytes);
			}
			Product createdProduct = productService.createProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

//		Product createdProduct = productService.createProduct(product);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@PutMapping("/{ProductId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
		try {
			Product savedProduct = productService.updateProduct(productId, updatedProduct);
			return new ResponseEntity<>(savedProduct, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<String>("Product successfully deleted!", HttpStatus.OK);
	}

}
