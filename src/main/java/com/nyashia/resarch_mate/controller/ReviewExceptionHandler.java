
package com.nyashia.resarch_mate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ReviewController.class)
public class ReviewExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleReviewError(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("The literature review service is temporarily unavailable. Please try again.");
    }
}
