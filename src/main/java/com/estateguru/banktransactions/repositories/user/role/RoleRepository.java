package com.estateguru.banktransactions.repositories.user.role;

import com.estateguru.banktransactions.models.entities.user.role.Role;
import com.estateguru.banktransactions.repositories.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CommonRepository<Role> {
}