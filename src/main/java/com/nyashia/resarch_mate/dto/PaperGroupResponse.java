package com.nyashia.resarch_mate.dto;

import java.time.Instant;

public record PaperGroupResponse(
        Long id,
        String name,
        long paperCount,
        Instant createdAt
) {
}
