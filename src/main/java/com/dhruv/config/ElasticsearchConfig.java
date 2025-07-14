package com.dhruv.config;

import org.apache.http.HttpHost;

import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticsearchConfig {
	
	@Value("${elasticsearch.host}")
	private String host;
	
	@Value("${elasticsearch.port}")
	private int port;
	
	@Bean
	public ElasticsearchClient elasticsearchClient(ObjectMapper objectMapper) {
		RestClient restClient = RestClient.builder(
				new HttpHost(host, port)).build();
		
		JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);
		RestClientTransport transport = new RestClientTransport(restClient, jsonpMapper);
		
		return new ElasticsearchClient(transport);
				
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		return mapper;
	}

}
