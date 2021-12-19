package com.estateguru.banktransactions.controllers;

import com.estateguru.banktransactions.models.dtos.ResponseModel;
import com.estateguru.banktransactions.models.dtos.account.AccountCreatingDto;
import com.estateguru.banktransactions.models.dtos.account.AccountFilterDto;
import com.estateguru.banktransactions.services.account.AccountService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("accounts/")
@RestController
public class AccountController extends BaseController {

    @Lazy
    private final AccountService mAccountService;

    public AccountController(AccountService accountService) {
        mAccountService = accountService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseModel<?> createAccount(@RequestBody AccountCreatingDto dto) {
        try {
            return createResult(mAccountService.createAccount(dto), "Account was created successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("user/{userId}")
    public ResponseModel<?> createAccount(@RequestBody AccountCreatingDto dto, @PathVariable Long userId) {
        try {
            return createResult(mAccountService.createAccount(dto, userId), "Account was created successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("list")
    public ResponseModel<?> getAccounts(@RequestBody AccountFilterDto dto) {
        try {
            return createResult(mAccountService.getAccounts(dto), "Accounts was retrieved successfully.");
        } catch (Exception e) {
            return createErrorResult(e);
        }
    }

}