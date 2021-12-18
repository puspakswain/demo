package com.ww.demo.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ww.demo.dto.ProductDetail;
import com.ww.demo.dto.ProductSkuDto;
import com.ww.demo.service.CategoryService;

@Repository
public class PageableProductRespositoryImpl implements PageableProductRespository {

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Autowired
	private CategoryService categoryService;
	  
	@Override
	public Page<ProductDetail> findProductsbyCategory(Pageable pageable, String categoryIds) {
		
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("product_Id");

		String producDetailQuery = "Select p.product_Id,p.category_Id,p.name, p.description, p.url, p.currency, sku.name as skuName, sku.description as skuDescription," 
				+ " sku.retail_price,sku.sale_price,sku.quantity,sku.inventory_type"
				+ " from product p, product_sku sku where p.product_id = sku.product_id"
				+ " and category_Id in (" + categoryIds +") ORDER BY " + order.getProperty()+
                " " + Sort.Direction.ASC.toString() +
                " OFFSET " + pageable.getOffset() + " ROWS" +
                " FETCH NEXT " + pageable.getPageSize() + " ROWS ONLY";;
		
		List<ProductDetail> productDetails =  
				jdbcTemplate.query(producDetailQuery, new ProductDetailMapper(),new Object[]{});
				
		
		return new PageImpl<>(productDetails, pageable, count(categoryIds));
	}
	
	
	 private final class ProductDetailMapper implements RowMapper<ProductDetail> {

	        @Override
	        public ProductDetail mapRow(ResultSet rs, int rowNum) throws SQLException {

	            if (rs == null) {
	                return null;
	            }

	            ProductDetail productDetail = new ProductDetail();
	            productDetail.setProductId(rs.getLong("product_id"));
	            productDetail.setName(rs.getString("name"));
	            productDetail.setDescription(rs.getString("description"));
	            productDetail.setCurrency(rs.getString("currency"));
	            productDetail.setUrl(rs.getString("url"));
	            productDetail.setCategory(categoryService.getCategoryMap().get(rs.getLong("category_Id")).getCategory());
	            productDetail.setSubCategory(categoryService.getCategoryMap().get(rs.getLong("category_Id")).getSubCategory());
	            
	            ProductSkuDto ProductSkuDto = new ProductSkuDto();
	            ProductSkuDto.setName(rs.getString("skuName"));
	            ProductSkuDto.setDescription(rs.getString("skuDescription"));
	            ProductSkuDto.setRetailPrice(rs.getDouble("retail_price"));
	            ProductSkuDto.setSalePrice(rs.getDouble("sale_price"));
	            ProductSkuDto.setQuantity(rs.getLong("quantity"));
	            ProductSkuDto.setInventoryType(rs.getString("inventory_type"));
	            
	            productDetail.setSku(ProductSkuDto);
	                             

	            return productDetail;
	        }
	 }
	 

	 private int count(String categoryIds) {
	        String countQuery =
	                "Select count(*) from product where category_Id in (" + categoryIds +")";
	        return jdbcTemplate.queryForObject(countQuery, Integer.class, new Object[]{});
	    }
	
	


}
