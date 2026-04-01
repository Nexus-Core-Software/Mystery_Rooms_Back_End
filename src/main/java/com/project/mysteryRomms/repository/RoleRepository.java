package com.project.mysteryRomms.repository;

import com.project.mysteryRomms.model.entity.Role;
import com.project.mysteryRomms.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
