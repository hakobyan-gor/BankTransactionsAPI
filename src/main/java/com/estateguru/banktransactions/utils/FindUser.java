package com.estateguru.banktransactions.utils;

import com.estateguru.banktransactions.exceptions.UnauthorizedException;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.repositories.user.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public record FindUser(@Lazy UserRepository userRepository) {

    public FindUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByToken() throws UnauthorizedException {
        Optional<User> optionalUser = userRepository.findByUsername(AuthenticationUtil.getAuthentication().getName());
        if (optionalUser.isEmpty())
            throw new UnauthorizedException("You are not authorized");
        return optionalUser.get();
    }

}