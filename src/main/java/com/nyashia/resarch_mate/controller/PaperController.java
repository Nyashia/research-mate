package com.nyashia.resarch_mate.controller;

import com.nyashia.resarch_mate.dto.PaperDto;
import com.nyashia.resarch_mate.service.PaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
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
}