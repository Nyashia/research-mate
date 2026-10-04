package com.nyashia.resarch_mate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nyashia.resarch_mate.model.PaperGroup;
import com.nyashia.resarch_mate.model.User;

public interface PaperGroupRepository extends JpaRepository<PaperGroup, Long> {

    List<PaperGroup> findByOwner(User owner);

    Optional<PaperGroup> findByIdAndOwner(Long id, User owner);
}