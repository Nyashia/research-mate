package com.nyashia.resarch_mate.client;

import com.nyashia.resarch_mate.dto.Author;
import com.nyashia.resarch_mate.dto.PaperDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("test-client")
public class SemanticScholarTester implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SemanticScholarTester.class);

    private final SemanticScholarClient client;

    public SemanticScholarTester(SemanticScholarClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        log.info("=== Testing Semantic Scholar client ===");

        try {
            List<PaperDto> papers = client.searchPapers("machine learning", 3);

            log.info("Found {} papers:", papers.size());
            for (PaperDto paper : papers) {
                log.info("---");
                log.info("Title: {}", paper.title());
                log.info("Year: {}", paper.year());
                log.info("Citations: {}", paper.citationCount());
                log.info("Authors: {}", paper.authors() != null
                        ? paper.authors().stream().map(Author::name).toList()
                        : "none");
            }
        } catch (Exception e) {
            log.error("Client test failed", e);
        }
    }
}