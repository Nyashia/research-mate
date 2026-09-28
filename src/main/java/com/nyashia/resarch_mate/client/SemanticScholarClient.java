package com.nyashia.resarch_mate.client;

import com.nyashia.resarch_mate.dto.PaperDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class SemanticScholarClient {

    private final RestClient restClient;

    public SemanticScholarClient(RestClient semanticScholarRestClient) {
        this.restClient = semanticScholarRestClient;
    }

    public List<PaperDto> searchPapers(String query, int limit) {
        SearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paper/search")
                        .queryParam("query", query)
                        .queryParam("limit", limit)
                        .queryParam("fields", "paperId,title,abstract,year,authors,citationCount,venue,openAccessPdf")
                        .build())
                .retrieve()
                .body(SearchResponse.class);

        return response != null ? response.data() : List.of();
    }

    public PaperDto getPaperById(String paperId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/paper/{paperId}")
                        .queryParam("fields", "paperId,title,abstract,year,authors,citationCount,venue,openAccessPdf")
                        .build(paperId))
                .retrieve()
                .body(PaperDto.class);
    }

    // Semantic Scholar wraps search results in {"total": N, "data": [...]}
    public record SearchResponse(int total, List<PaperDto> data) {}
}