package com.example.login.repository;

import com.example.login.domain.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy,Long> {
    RoleHierarchy findByChildName(String roleName);
}
