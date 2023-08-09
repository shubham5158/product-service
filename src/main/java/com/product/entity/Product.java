package com.product.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@NotNull(message = "Name is required")
	@Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
	private String name;

	@NotNull(message = "Price is required")
	@Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Price must be a positive number with up to 2 decimal places")
	private String price;

	@NotNull(message = "Description is required")
	private String discription;

	@NotNull(message = "Image URL is required")
	@Lob
	private byte[] imageUrl;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "cartId")
	private Cart cart;

}
