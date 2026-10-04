package com.nyashia.resarch_mate.dto;

import java.time.Instant;

public record SavedPaperResponse(
        Long id,
        String paperId,
        String title,
        Instant savedAt
) {
}