package com.nyashia.resarch_mate.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record ReviewRequest(
        @NotEmpty(message = "At least one paper ID is required")
        List<String> paperIds
) {}