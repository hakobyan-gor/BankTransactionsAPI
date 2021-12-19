package com.estateguru.banktransactions.repositories.user;

import com.estateguru.banktransactions.repositories.CommonRepository;
import com.estateguru.banktransactions.models.entities.user.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CommonRepository<User> {

    @Query("SELECT u FROM User u WHERE u.hidden = FAlSE AND u.username = :username")
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndHiddenFalse(String username);

}