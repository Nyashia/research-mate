package com.nyashia.resarch_mate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nyashia.resarch_mate.model.PaperGroup;
import com.nyashia.resarch_mate.model.SavedPaper;

public interface SavedPaperRepository extends JpaRepository<SavedPaper, Long> {

    long countByGroup(PaperGroup group);

    boolean existsByPaperIdAndGroup(String paperId, PaperGroup group);
}