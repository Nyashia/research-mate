package com.nyashia.resarch_mate.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.nyashia.resarch_mate.dto.PaperDto;


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

    public List<PaperDto> getPapersByIds(List<String> paperIds) {

        List<CompletableFuture<PaperDto>> futures = paperIds.stream()
                .map(paperId -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return getPaperById(paperId);
                    } catch (Exception e) {
                        System.err.println(
                                "Could not fetch paper " + paperId + ": " + e.getMessage());
                        return null;
                    }
                }))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(paper -> paper != null)
                .filter(paper -> paper.abstractText() != null
                        && !paper.abstractText().isBlank())
                .toList();
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
    public record SearchResponse(int total, List<PaperDto> data) {
    }
}