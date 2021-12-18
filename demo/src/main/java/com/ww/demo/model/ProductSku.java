package com.ww.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ForeignKey;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "Product_Sku")
public class ProductSku implements Serializable {


	private static final long serialVersionUID = 1049133824127735156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long skuCode;
	
	@OneToOne
	@JoinColumn(nullable = false,
	      foreignKey = @ForeignKey(name = "PRODUCTSKU_FK_PRODUCT_ID"))
	private Product product;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Long productId;
	
	@Column(nullable = false)
	private String description;
	

	@Column(nullable = false)
	private Double retailPrice;
	
	@Column(nullable = false)
	private Double salePrice;
	
	@Column(nullable = false)
	private Long quantity;
	
	@Column(nullable = false)
	private String inventoryType;
		
	@Column(nullable = false)
	private LocalDateTime creationDateTime;	
	
	@Column(nullable = false)
	private String creationUser;
	
	private LocalDateTime lastUpdateDateTime;	
	private String lastUpdateUser;



}
