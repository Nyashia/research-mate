package com.nyashia.resarch_mate.repository;

import com.nyashia.resarch_mate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}