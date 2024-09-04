package com.user.management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.management.model.RoleName;
import com.user.management.model.Roles;


public interface RolesRepository extends JpaRepository<Roles, String> {

	Optional<Roles> findByRoleName(RoleName roleName);

	boolean existsByRoleName(RoleName admin);

}
