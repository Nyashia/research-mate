package com.nyashia.resarch_mate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nyashia.resarch_mate.dto.SavePaperRequest;
import com.nyashia.resarch_mate.dto.SavedPaperResponse;
import com.nyashia.resarch_mate.model.User;
import com.nyashia.resarch_mate.service.SavedPaperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SavedPaperController {

    private final SavedPaperService savedPaperService;

    public SavedPaperController(SavedPaperService savedPaperService) {
        this.savedPaperService = savedPaperService;
    }

    @PostMapping("/groups/{groupId}/papers")
    public ResponseEntity<SavedPaperResponse> savePaper(
            @PathVariable Long groupId,
            @Valid @RequestBody SavePaperRequest request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                savedPaperService.savePaper(
                        groupId,
                        request,
                        user
                )
        );
    }

    @GetMapping("/groups/{groupId}/papers")
    public ResponseEntity<List<SavedPaperResponse>> listPapers(
            @PathVariable Long groupId,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                savedPaperService.listPapers(
                        groupId,
                        user
                )
        );
    }

    @DeleteMapping("/papers/saved/{id}")
    public ResponseEntity<Void> removePaper(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        savedPaperService.removePaper(id, user);

        return ResponseEntity.noContent().build();
    }
}