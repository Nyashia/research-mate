package com.nyashia.resarch_mate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaperDto(
    String paperId,
    String title,
    @JsonProperty("abstract") String abstractText,
    Integer year,
    List<Author> authors,
    Integer citationCount,
    String venue,
    OpenAccessPdf openAccessPdf
) {}