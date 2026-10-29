
package com.nyashia.resarch_mate.controller;

import com.nyashia.resarch_mate.dto.ReviewRequest;
import com.nyashia.resarch_mate.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateReview(
            @Valid @RequestBody ReviewRequest request
    ) {
        return reviewService.generateReview(request.paperIds());
    }
}
