package com.springspecification.repository;

import com.springspecification.model.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<InternalUser, Long> {
}