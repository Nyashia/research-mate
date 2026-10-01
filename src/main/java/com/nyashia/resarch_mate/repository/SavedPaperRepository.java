package com.nyashia.resarch_mate.repository;

import com.nyashia.resarch_mate.model.SavedPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedPaperRepository extends JpaRepository<SavedPaper, Long> {
}