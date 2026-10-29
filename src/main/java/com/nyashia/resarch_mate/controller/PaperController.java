package com.nyashia.resarch_mate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nyashia.resarch_mate.dto.PaperDto;
import com.nyashia.resarch_mate.dto.SummaryResponse;
import com.nyashia.resarch_mate.service.PaperService;
import com.nyashia.resarch_mate.service.SummarizationService;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    private final PaperService paperService;
    private final SummarizationService summarizationService;

    public PaperController(
            PaperService paperService,
            SummarizationService summarizationService) {
        this.paperService = paperService;
        this.summarizationService = summarizationService;
    }

    @GetMapping("/search")
    public List<PaperDto> search(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return paperService.search(query, limit);
    }

    @GetMapping("/{paperId}")
    public PaperDto getById(@PathVariable String paperId) {
        return paperService.getById(paperId);
    }

    @GetMapping("/{paperId}/summary")
    public SummaryResponse summarize(@PathVariable String paperId) {

        PaperDto paper = paperService.getById(paperId);

        if (paper.abstractText() == null || paper.abstractText().isBlank()) {
            throw new IllegalArgumentException("This paper does not have an abstract");
        }

        String summary = summarizationService.summarize(
                paper.paperId(),
                paper.abstractText());

        return new SummaryResponse(
                paper.paperId(),
                paper.title(),
                summary);
    }

}