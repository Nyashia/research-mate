package com.nyashia.resarch_mate.service;

import com.nyashia.resarch_mate.dto.CreateGroupRequest;
import com.nyashia.resarch_mate.dto.PaperGroupResponse;
import com.nyashia.resarch_mate.model.PaperGroup;
import com.nyashia.resarch_mate.model.User;
import com.nyashia.resarch_mate.repository.PaperGroupRepository;
import com.nyashia.resarch_mate.repository.SavedPaperRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PaperGroupService {

    private final PaperGroupRepository paperGroupRepository;
    private final SavedPaperRepository savedPaperRepository;

    public PaperGroupService(
            PaperGroupRepository paperGroupRepository,
            SavedPaperRepository savedPaperRepository) {

        this.paperGroupRepository = paperGroupRepository;
        this.savedPaperRepository = savedPaperRepository;
    }

    public PaperGroupResponse createGroup(
            CreateGroupRequest request,
            User owner) {

        PaperGroup group = new PaperGroup();

        group.setName(request.name());
        group.setOwner(owner);
        group.setCreatedAt(Instant.now());

        PaperGroup savedGroup = paperGroupRepository.save(group);

        return toResponse(savedGroup);
    }

    public List<PaperGroupResponse> listGroups(User owner) {

        return paperGroupRepository.findByOwner(owner)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteGroup(Long groupId, User owner) {

        PaperGroup group = paperGroupRepository
                .findByIdAndOwner(groupId, owner)
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "You do not have access to this group"
                        ));

        paperGroupRepository.delete(group);
    }

    private PaperGroupResponse toResponse(PaperGroup group) {

        long paperCount =
                savedPaperRepository.countByGroup(group);

        return new PaperGroupResponse(
                group.getId(),
                group.getName(),
                paperCount,
                group.getCreatedAt()
        );
    }
}