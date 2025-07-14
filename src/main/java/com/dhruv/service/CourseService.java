package com.dhruv.service;

import java.util.List;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhruv.model.CourseDocument;
import com.dhruv.model.CourseSearchRequest;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CourseService {
	
	@Autowired
	private ElasticsearchClient elasticsearchClient;
	

	public SearchResponseWrapper search(CourseSearchRequest request) throws Exception{	
		
			var searchRequest = elasticsearchClient.search(s -> {
	            s.index("courses");

	            // Query
	            s.query(q -> q.bool(b -> {
	                if (request.getQ() != null && !request.getQ().isEmpty()) {
	                    b.must(m -> m.multiMatch(mm -> mm
	                        .fields("title", "description")
	                        .query(request.getQ())
	                        .fuzziness("AUTO")
	                    ));
	                }

	                if (request.getCategory() != null) {
	                    b.filter(f -> f.term(t -> t.field("category.keyword").value(request.getCategory())));
	                }

	                if (request.getType() != null) {
	                    b.filter(f -> f.term(t -> t.field("type.keyword").value(request.getType())));
	                }

	                if (request.getMinAge() != null || request.getMaxAge() != null) {
	                    b.filter(f -> f.range(r -> {
	                        r.field("minAge");
	                        if (request.getMinAge() != null) r.gte(JsonData.of(request.getMinAge()));
	                        if (request.getMaxAge() != null) r.lte(JsonData.of(request.getMaxAge()));
	                        return r;
	                    }));
	                }


	                if (request.getMinPrice() != null || request.getMaxPrice() != null) {
	                    b.filter(f -> f.range(r -> {
	                        r.field("price");
	                        if (request.getMinPrice() != null) r.gte(JsonData.of(request.getMinPrice()));
	                        if (request.getMaxPrice() != null) r.lte(JsonData.of(request.getMaxPrice()));
	                        return r;
	                    }));
	                }


	                if (request.getStartDate() != null) {
	                    b.filter(f -> f.range(r -> r
	                        .field("nextSessionDate")
	                        .gte(JsonData.of(request.getStartDate()))
	                    ));
	                }


	                return b;
	            }));

	            // Sorting
	            if ("priceAsc".equalsIgnoreCase(request.getSort())) {
	                s.sort(sort -> sort.field(f -> f.field("price").order(SortOrder.Asc)));
	            } else if ("priceDesc".equalsIgnoreCase(request.getSort())) {
	                s.sort(sort -> sort.field(f -> f.field("price").order(SortOrder.Desc)));
	            } else {
	                s.sort(sort -> sort.field(f -> f.field("nextSessionDate").order(SortOrder.Asc)));
	            }

	            // Pagination
	            s.from(request.getPage() * request.getSize());
	            s.size(request.getSize());

	            return s;
	        }, CourseDocument.class);

	        List<CourseDocument> results = searchRequest.hits().hits().stream()
	                .map(Hit::source)
	                .collect(Collectors.toList());

	        return new SearchResponseWrapper(searchRequest.hits().total().value(), results);
		
	}


	public List<String> getSuggestion(String query) {
		
		try {
			SearchResponse<Void> response = elasticsearchClient.search(s -> s
					.index("courses")
					.suggest(sg -> sg
							.suggesters("course-suggest", sugg -> sugg
									.prefix(query)
									.completion(c -> c
											.field("suggest")
											.skipDuplicates(true)
											.size(10)
									)
							)
					),
					Void.class
			);
			
			return response.suggest()
					.get("course-suggest")
					.stream()
					.flatMap(entry -> entry.completion().options().stream())
					.map(CompletionSuggestOption::text)
					.map(Object::toString)
					.collect(Collectors.toList());
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch suggestions", e);
		}
	}
}
