package com.estateguru.banktransactions.services.account;

import com.estateguru.banktransactions.models.dtos.account.AccountListPreviewDto;
import com.estateguru.banktransactions.models.entities.lastNumber.LastNumbers;
import com.estateguru.banktransactions.repositories.account.AccountRepository;
import com.estateguru.banktransactions.models.dtos.account.AccountCreatingDto;
import com.estateguru.banktransactions.models.dtos.account.AccountFilterDto;
import com.estateguru.banktransactions.repositories.lastNumber.LastNumbersRepository;
import com.estateguru.banktransactions.specifications.AccountSpecification;
import com.estateguru.banktransactions.exceptions.EntityNotFoundException;
import com.estateguru.banktransactions.repositories.user.UserRepository;
import com.estateguru.banktransactions.models.entities.account.Account;
import com.estateguru.banktransactions.exceptions.MessageException;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.models.enums.CurrencyEnum;
import com.estateguru.banktransactions.services.AbstractService;
import com.estateguru.banktransactions.utils.FindUser;

import com.estateguru.banktransactions.utils.GenerateUniqueIdUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class AccountServiceImpl extends AbstractService<Account, AccountRepository> implements AccountService {

    @Lazy
    private final LastNumbersRepository mLastNumbersRepository;
    @Lazy
    private final AccountSpecification mAccountSpecification;
    @Lazy
    private final AccountRepository mAccountRepository;
    @Lazy
    private final UserRepository mUserRepository;
    @Lazy
    private final FindUser mFindUser;

    public AccountServiceImpl(
            LastNumbersRepository lastNumbersRepository,
            AccountSpecification accountSpecification,
            UserRepository userRepository,
            AccountRepository repository,
            FindUser findUser
    ) {
        super(repository);
        mLastNumbersRepository = lastNumbersRepository;
        mAccountSpecification  = accountSpecification;
        mUserRepository        = userRepository;
        mAccountRepository     = repository;
        mFindUser              = findUser;
    }

    @Override
    public Long createAccount(AccountCreatingDto dto) throws MessageException, EntityNotFoundException {
        User user = mFindUser.findUserByToken();
        CurrencyEnum currencyEnum = getCurrencyEnum(dto.currency(), user);
        LastNumbers lastNumbers = mLastNumbersRepository.findAll().get(0);
        lastNumbers.setAccountLastNumber(lastNumbers.getAccountLastNumber() + 1);
        String accountNumber = GenerateUniqueIdUtil.generateId((long) (lastNumbers.getAccountLastNumber() + 1));
        return save(Account
                .builder()
                .currency(currencyEnum)
                .accountNumber(accountNumber)
                .user(user)
                .cost(0D)
                .build()).getId();
    }

    @Override
    public Long createAccount(AccountCreatingDto dto, Long userId) throws EntityNotFoundException, MessageException {
        Optional<User> optionalUser = mUserRepository.findByIdAndHiddenFalse(userId);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException(User.class, "Id", userId.toString());
        CurrencyEnum currencyEnum = getCurrencyEnum(dto.currency(), optionalUser.get());
        LastNumbers lastNumbers = mLastNumbersRepository.findAll().get(0);
        lastNumbers.setAccountLastNumber(lastNumbers.getAccountLastNumber() + 1);
        String accountNumber = GenerateUniqueIdUtil.generateId((long) (lastNumbers.getAccountLastNumber() + 1));
        return save(Account
                .builder()
                .accountNumber(accountNumber)
                .user(optionalUser.get())
                .currency(currencyEnum)
                .cost(0D)
                .build()).getId();
    }

    @Override
    public List<AccountListPreviewDto> getAccounts(AccountFilterDto dto) {
        User user = mFindUser.findUserByToken();
        return mAccountSpecification.filterAccounts(dto, user.getId());
    }

    private CurrencyEnum getCurrencyEnum(int currency, User user) throws MessageException {
        CurrencyEnum currencyEnum = CurrencyEnum.valueOf(currency).orElseThrow(
                () -> new MessageException("Incorrect currency."));
        if (mAccountRepository.existsByCurrencyAndUser(currencyEnum, user))
            throw new MessageException("User have account with given currency.");
        return currencyEnum;
    }

}
