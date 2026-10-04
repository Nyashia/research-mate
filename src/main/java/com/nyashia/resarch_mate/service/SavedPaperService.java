package com.nyashia.resarch_mate.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nyashia.resarch_mate.dto.SavePaperRequest;
import com.nyashia.resarch_mate.dto.SavedPaperResponse;
import com.nyashia.resarch_mate.model.PaperGroup;
import com.nyashia.resarch_mate.model.SavedPaper;
import com.nyashia.resarch_mate.model.User;
import com.nyashia.resarch_mate.repository.PaperGroupRepository;
import com.nyashia.resarch_mate.repository.SavedPaperRepository;

@Service
public class SavedPaperService {

    private final SavedPaperRepository savedPaperRepository;
    private final PaperGroupRepository paperGroupRepository;

    public SavedPaperService(
            SavedPaperRepository savedPaperRepository,
            PaperGroupRepository paperGroupRepository) {

        this.savedPaperRepository = savedPaperRepository;
        this.paperGroupRepository = paperGroupRepository;
    }

    public SavedPaperResponse savePaper(
            Long groupId,
            SavePaperRequest request,
            User owner) {

        PaperGroup group = paperGroupRepository
                .findByIdAndOwner(groupId, owner)
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "You do not have access to this group"
                        ));

        if (savedPaperRepository.existsByPaperIdAndGroup(
                request.paperId(),
                group)) {

            throw new IllegalArgumentException(
                    "Paper is already saved in this group"
            );
        }

        SavedPaper paper = new SavedPaper();

        paper.setPaperId(request.paperId());
        paper.setTitle(request.title());
        paper.setGroup(group);
        paper.setSavedAt(Instant.now());

        SavedPaper savedPaper = savedPaperRepository.save(paper);

        return toResponse(savedPaper);
    }

    public List<SavedPaperResponse> listPapers(
            Long groupId,
            User owner) {

        PaperGroup group = paperGroupRepository
                .findByIdAndOwner(groupId, owner)
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "You do not have access to this group"
                        ));

        return savedPaperRepository.findAll()
                .stream()
                .filter(paper -> paper.getGroup().getId().equals(group.getId()))
                .map(this::toResponse)
                .toList();
    }

    public void removePaper(
            Long savedPaperId,
            User owner) {

        SavedPaper paper = savedPaperRepository
                .findById(savedPaperId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Saved paper not found"
                        ));

        if (!paper.getGroup().getOwner().getId().equals(owner.getId())) {
            throw new AccessDeniedException(
                    "You do not have access to this saved paper"
            );
        }

        savedPaperRepository.delete(paper);
    }

    private SavedPaperResponse toResponse(SavedPaper paper) {

        return new SavedPaperResponse(
                paper.getId(),
                paper.getPaperId(),
                paper.getTitle(),
                paper.getSavedAt()
        );
    }
}
