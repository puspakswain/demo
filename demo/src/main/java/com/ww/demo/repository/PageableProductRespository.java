package com.ww.demo.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ww.demo.dto.ProductDetail;

public interface PageableProductRespository {
	
    Page<ProductDetail> findProductsbyCategory(Pageable pageable,String categoryIds);


}
