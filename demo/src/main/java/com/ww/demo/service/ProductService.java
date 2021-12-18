package com.ww.demo.service;

import org.springframework.data.domain.Pageable;

import com.ww.demo.dto.ProductDetailView;
import com.ww.demo.dto.ProductDto;
import com.ww.demo.dto.ProductSkuDto;
import com.ww.demo.model.Product;
import com.ww.demo.model.ProductSku;

public interface ProductService {
	  public Product saveProduct(ProductDto productDto, Long productId);
	  public void deleteProduct(Long productId);
	  
	  public ProductSku addProductSku(ProductSkuDto ProductSkuDto, Long productId);
	  public ProductSku updateProductSku(ProductSkuDto ProductSkuDto, Long skuCode);

	  
	  public ProductDetailView findProductsbyCategory(Pageable pageable, String category);
	  
}
