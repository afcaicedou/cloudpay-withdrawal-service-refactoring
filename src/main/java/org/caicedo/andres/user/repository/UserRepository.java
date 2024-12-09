package org.caicedo.andres.user.repository;

import org.caicedo.andres.user.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}
