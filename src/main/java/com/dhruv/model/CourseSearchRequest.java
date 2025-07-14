package com.dhruv.model;

import java.time.Instant;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class CourseSearchRequest {
	
	private String q;
	private String category;
	private String type;
	
	private Integer minAge;
	private Integer maxAge;
	
	private Double minPrice;
	private Double maxPrice;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Instant startDate;
	
	private String sort;
	int page = 0;
	int size = 50;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Instant getStartDate() {
		return startDate;
	}
	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
