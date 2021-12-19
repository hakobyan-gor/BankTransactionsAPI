package com.estateguru.banktransactions.controllers;

import com.estateguru.banktransactions.models.dtos.ResponseModel;
import com.estateguru.banktransactions.models.dtos.user.UserCreatingDto;
import com.estateguru.banktransactions.services.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("users/")
@RestController
public class UserController extends BaseController {

    @Lazy
    private final UserService mUserService;

    public UserController(UserService userService) {this.mUserService = userService;}

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseModel<?> createUser(@RequestBody @Valid UserCreatingDto dto) {
        try {
            return createResult(mUserService.createUser(dto), "User was created successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{page}/{size}")
    public ResponseModel<?> getUsers(@PathVariable int page, @PathVariable int size) {
        try {
            return createResult(mUserService.getUsers(page, size), "Users were retrieved successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @GetMapping("detail/")
    public ResponseModel<?> getUserDetails() {
        try {
            return createResult(mUserService.getUserDetails(), "User Details retrieved successfully");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

}