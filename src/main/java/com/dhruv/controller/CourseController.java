package com.dhruv.controller;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhruv.model.CourseSearchRequest;
import com.dhruv.service.CourseService;
import com.dhruv.service.SearchResponseWrapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.security.SuggestUserProfilesRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {
	
	@Autowired
	private CourseService service;
	
		
	@GetMapping("/search")
	public SearchResponseWrapper search(CourseSearchRequest request) throws Exception {
		return service.search(request);
	}
	
	@GetMapping("/search/suggest")
	public List<String> suggest(@RequestParam("q") String keyword) throws IOException{
		return service.getSuggestion(keyword);
	}
	
	

}
