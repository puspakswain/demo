package com.ww.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "Category" ,uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_category_subCategory",columnNames = {"category", "subCategory"})})
public class Category implements Serializable {
	
	private static final long serialVersionUID = 1049133824127735957L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long categoryId;

	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private String subCategory;
	
	@Column(nullable = false)
	private Boolean isActive;
	
	@Column(nullable = false)
	private LocalDateTime creationDateTime;	
	
	@Column(nullable = false)
	private String creationUser;
	
	private LocalDateTime lastUpdateDateTime;	
	private String lastUpdateUser;


}
