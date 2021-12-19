package com.estateguru.banktransactions.repositories.account;

import com.estateguru.banktransactions.models.entities.account.Account;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.models.enums.CurrencyEnum;
import com.estateguru.banktransactions.repositories.CommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CommonRepository<Account> {

    boolean existsByCurrencyAndUser(CurrencyEnum currencyEnum, User user);

}