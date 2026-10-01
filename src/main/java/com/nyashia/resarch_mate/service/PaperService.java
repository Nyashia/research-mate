package com.nyashia.resarch_mate.service;

import com.nyashia.resarch_mate.client.SemanticScholarClient;
import com.nyashia.resarch_mate.dto.PaperDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {

    private static final Logger log = LoggerFactory.getLogger(PaperService.class);

    private final SemanticScholarClient client;

    public PaperService(SemanticScholarClient client) {
        this.client = client;
    }

    @Cacheable(value = "paper-searches", key = "#query + ':' + #limit")
    public List<PaperDto> search(String query, int limit) {
        log.info("Cache MISS — calling Semantic Scholar for query='{}', limit={}", query, limit);
        return client.searchPapers(query, limit);
    }

    @Cacheable(value = "papers", key = "#paperId")
    public PaperDto getById(String paperId) {
        log.info("Cache MISS — calling Semantic Scholar for paperId='{}'", paperId);
        return client.getPaperById(paperId);
    }
}