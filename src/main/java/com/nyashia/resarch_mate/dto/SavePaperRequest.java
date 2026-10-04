package com.nyashia.resarch_mate.dto;

import jakarta.validation.constraints.NotBlank;

public record SavePaperRequest(

        @NotBlank
        String paperId,

        @NotBlank
        String title,

        String abstractText,

        Integer year
) {
}