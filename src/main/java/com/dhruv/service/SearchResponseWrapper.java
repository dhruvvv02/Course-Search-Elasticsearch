package com.dhruv.service;

import java.util.List;

import com.dhruv.model.CourseDocument;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResponseWrapper {
	
	private long total;
	private List<CourseDocument> courses;
	
	public SearchResponseWrapper(long value, List<CourseDocument> results) {
		// TODO Auto-generated constructor stub
		this.total = value;
		this.courses = results;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<CourseDocument> getCourses() {
		return courses;
	}
	public void setCourses(List<CourseDocument> courses) {
		this.courses = courses;
	}
	
	

}
