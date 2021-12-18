package com.ww.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ww.demo.dto.ProductDetail;
import com.ww.demo.dto.ProductDetailView;
import com.ww.demo.dto.ProductDto;
import com.ww.demo.dto.ProductSkuDto;
import com.ww.demo.exception.NotFoundException;
import com.ww.demo.model.Category;
import com.ww.demo.model.Product;
import com.ww.demo.model.ProductSku;
import com.ww.demo.repository.CategoryRepository;
import com.ww.demo.repository.PageableProductRespository;
import com.ww.demo.repository.ProductRepository;
import com.ww.demo.repository.ProductSkuRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	 @Autowired
	 private ProductRepository productRepository;
	 
	 @Autowired
	 private CategoryRepository categoryRepository;
	 
	 @Autowired
	 private CategoryService categoryService;
	 
	 @Autowired
	 private ProductSkuRepository productSkuRepository;
	 
	 @Autowired
	 private PageableProductRespository pageableProductRespository;
	  
	@Override
	public Product saveProduct(ProductDto productDto ,  Long productId) {
		Product product = null;
		if(productId == null) {
			product = new Product();
		} else {
			product = productRepository.findById(productId)
		            .orElseThrow(() -> new NotFoundException("Product Not Found"));
		}
		Category category = categoryRepository.findByCategoryAndSubCategory(productDto.getCategory(), productDto.getSubCategory())
				  .orElseThrow(() -> new NotFoundException("Catgeory Not Found"));
		product.setName(productDto.getName());
		product.setCategory(category);
		product.setCategoryId(category.getCategoryId());
		product.setDescription(productDto.getDescription());
		product.setUrl(productDto.getUser());
		product.setCurrency(productDto.getCurrency());
		product.setCreationUser(productDto.getUser());
		product.setCreationDateTime(LocalDateTime.now());
		return productRepository.save(product);
	}



	@Override
	public void deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new NotFoundException("Product Not Found"));
	       productRepository.delete(product);
	}


	@Override
	public ProductSku addProductSku(ProductSkuDto ProductSkuDto, Long productId) {
		ProductSku productSku = new ProductSku();
		Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new NotFoundException("Product Not Found"));		
		BeanUtils.copyProperties(ProductSkuDto, productSku);
		productSku.setProductId(product.getProductId());
		productSku.setProduct(product);
		productSku.setCreationDateTime(LocalDateTime.now());
		productSku.setCreationUser(ProductSkuDto.getUser());

		return productSkuRepository.save(productSku);
	}
	

	@Override
	public ProductSku updateProductSku(ProductSkuDto ProductSkuDto, Long skuCode) {
		ProductSku productSku = productSkuRepository.findById(skuCode)
	            .orElseThrow(() -> new NotFoundException("Product Sku Not Found"));	
		BeanUtils.copyProperties(ProductSkuDto, productSku);

		return productSkuRepository.save(productSku);
	}



	@Override
	public ProductDetailView findProductsbyCategory(Pageable pageable, String category) {
		
		List<Long> categoryIdlist = categoryService.findAll().stream().filter(cat -> {
			return category.equals(cat.getCategory());
		}).collect(Collectors.toList()).stream().map(subCategory -> subCategory.getCategoryId()).collect(Collectors.toList());;
		
		Page<ProductDetail> productDetailPageble = pageableProductRespository.findProductsbyCategory(pageable, StringUtils.join(categoryIdlist, ','));
        List<ProductDetail> products = productDetailPageble.getContent();
        
        ProductDetailView productDetailView = new ProductDetailView();
        productDetailView.setProducts(products);
        productDetailView.setTotalCount(productDetailPageble.getTotalElements());
        
        
        return productDetailView;
	}

}
