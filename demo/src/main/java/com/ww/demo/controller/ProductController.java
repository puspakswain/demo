package com.ww.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ww.demo.dto.ProductDto;
import com.ww.demo.dto.ProductSkuDto;
import com.ww.demo.model.Product;
import com.ww.demo.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Tag(description = "Add/Edit/Delete/Get", name = "APIs")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Add new product")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Object> addProduct(@RequestBody ProductDto productDto) {
		log.debug("Product Inputs: " + productDto);
		return CompletableFuture.supplyAsync(() -> {
			Product product = productService.saveProduct(productDto, null);
			return product;
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);
	}


	@PutMapping("/{productId}")
	@Operation(description = "Update Product Details for a Product Id")
	public CompletableFuture<Object> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {
		log.debug("Product Id: " + productId + "Data: " + productDto);
		return CompletableFuture.supplyAsync(() -> {
			Product product = productService.saveProduct(productDto, productId);
			return product;
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);

	}

	@DeleteMapping("/{productId}")
	@Operation(description = "Delete Product for a Product Id")
	public CompletableFuture<Object> deleteProduct(@PathVariable Long productId) {

		log.debug("Product Id: " + productId);
		return CompletableFuture.supplyAsync(() -> {
			productService.deleteProduct(productId);
			return "Product with productId: " + productId + " Deleted !";
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);

	}

	@PostMapping(value = "/{productId}/sku/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Add new product Sku")
	@ResponseStatus(code = HttpStatus.OK)
	public CompletableFuture<Object> addProductSku(@RequestBody ProductSkuDto productSkuDto,
			@PathVariable Long productId) {
		log.debug("Product Sku Inputs: " + productSkuDto);
		return CompletableFuture.supplyAsync(() -> {
			return productService.addProductSku(productSkuDto, productId);
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);
	}
	
	@PutMapping("/{productId}/sku/{skuCode}")
	@Operation(description = "Update Product Sku Details for a Sku code")
	public CompletableFuture<Object> updateProductSku(@RequestBody ProductSkuDto productSkuDto, @PathVariable Long productId, @PathVariable Long skuCode) {
		log.debug("Product Id: " + productId + "Data: " + productSkuDto + "Sku Code:"+ skuCode);
		return CompletableFuture.supplyAsync(() -> {
			return productService.updateProductSku(productSkuDto, skuCode);
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);

	}
	

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Get the Product details for a product category")
	@ResponseStatus(value = HttpStatus.OK)
	public CompletableFuture<Object> findProductsbyCategory(@RequestParam String category, @RequestParam int page,
			@RequestParam int size) {

		return CompletableFuture.supplyAsync(() -> {
			return productService.findProductsbyCategory(PageRequest.of(page, size), category);
		}).handle((result, throwable) -> throwable != null ? throwable.getCause() : result);

	}

}
