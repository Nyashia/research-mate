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

import com.nyashia.resarch_mate.dto.CreateGroupRequest;
import com.nyashia.resarch_mate.dto.PaperGroupResponse;
import com.nyashia.resarch_mate.model.User;
import com.nyashia.resarch_mate.service.PaperGroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class PaperGroupController {

    private final PaperGroupService paperGroupService;

    public PaperGroupController(PaperGroupService paperGroupService) {
        this.paperGroupService = paperGroupService;
    }

    @PostMapping
    public ResponseEntity<PaperGroupResponse> createGroup(
            @Valid @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                paperGroupService.createGroup(request, user)
        );
    }

    @GetMapping
    public ResponseEntity<List<PaperGroupResponse>> listGroups(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                paperGroupService.listGroups(user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        paperGroupService.deleteGroup(id, user);

        return ResponseEntity.noContent().build();
    }
}