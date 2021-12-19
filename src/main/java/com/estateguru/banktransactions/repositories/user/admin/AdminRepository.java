package com.estateguru.banktransactions.repositories.user.admin;

import com.estateguru.banktransactions.models.entities.user.Admin;
import com.estateguru.banktransactions.repositories.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CommonRepository<Admin> {
}