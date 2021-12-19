package com.estateguru.banktransactions.services.account;

import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.exceptions.MessageException;
import com.estateguru.banktransactions.models.dtos.account.AccountCreatingDto;
import com.estateguru.banktransactions.models.dtos.account.AccountFilterDto;
import com.estateguru.banktransactions.models.dtos.account.AccountListPreviewDto;
import com.estateguru.banktransactions.models.entities.account.Account;
import com.estateguru.banktransactions.services.CommonService;

import java.util.List;

public interface AccountService extends CommonService<Account> {

    Long createAccount(AccountCreatingDto dto) throws MessageException, EntityNotFoundException;

    Long createAccount(AccountCreatingDto dto, Long userId) throws EntityNotFoundException, MessageException;

    List<AccountListPreviewDto> getAccounts(AccountFilterDto dto);

}