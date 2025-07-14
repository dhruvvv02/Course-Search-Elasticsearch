package com.dhruv.model;

import java.io.InputStream;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataLoader.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ElasticsearchClient elasticsearchClient;
	
	@PostConstruct
	public void loadData() {
		try(InputStream inputStream = getClass().getResourceAsStream("/sample-courses.json")){
			if(inputStream == null) {
				log.error("sample-courses.json not found in the resources");
				return;
			}
			
			List<CourseDocument> courses = objectMapper.readValue(inputStream,
					new TypeReference<List<CourseDocument>>() {});
			
			for(CourseDocument course : courses) {
				course.setSuggest(List.of(course.getTitle()));
			}
			
			List<BulkOperation> operations = courses.stream()
					.map(course -> BulkOperation.of(op -> op
							.index(idx -> idx
									.index("courses")
									.id(course.getId())
									.document(course)
									)
					))
					.collect(Collectors.toList());
			
			elasticsearchClient.bulk(b -> b
					.index("courses")
					.operations(operations)
			);
			
			log.info("Successfully indexed {} courses into Elasticsearch", courses.size());
		}catch (Exception e) {
			log.error("Failed to laod or index sample course data", e);
		}
	}
	
	

}
