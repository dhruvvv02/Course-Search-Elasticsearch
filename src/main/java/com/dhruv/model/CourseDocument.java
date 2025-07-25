package com.dhruv.model;

import java.time.Instant;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDocument {
	
	
	private String id;
	
	private String title;
	
	private String description;
	
	private String category;
	
	private String type;
	
	private String gradeRange;
	
	private Integer minAge;
	
	private Integer maxAge;
	
	private Double price;
	
	private Instant nextSessionDate;
	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> suggest;
	
	public CourseDocument() {
		
	}

	public CourseDocument(String id, String title, String description, String category, String type, String gradeRange,
			Integer minAge, Integer maxAge, Double price, Instant nextSessionDate, List<String> suggest) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.type = type;
		this.gradeRange = gradeRange;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.price = price;
		this.nextSessionDate = nextSessionDate;
		this.suggest = suggest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getGradeRange() {
		return gradeRange;
	}

	public void setGradeRange(String gradeRange) {
		this.gradeRange = gradeRange;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Instant getNextSessionDate() {
		return nextSessionDate;
	}

	public void setNextSessionDate(Instant nextSessionDate) {
		this.nextSessionDate = nextSessionDate;
	}

	public List<String> getSuggest() {
		return suggest;
	}

	public void setSuggest(List<String> suggest) {
		this.suggest = suggest;
	}
	
	
	
	
	

}
