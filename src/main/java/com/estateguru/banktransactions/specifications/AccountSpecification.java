package com.estateguru.banktransactions.specifications;

import com.estateguru.banktransactions.models.dtos.account.AccountFilterDto;
import com.estateguru.banktransactions.models.dtos.account.AccountListPreviewDto;
import com.estateguru.banktransactions.models.entities.account.Account;
import com.estateguru.banktransactions.models.entities.user.User;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

@Component
public class AccountSpecification {

    private final EntityManager mEntityManager;

    public AccountSpecification(EntityManager entityManager) {
        mEntityManager = entityManager;
    }

    public List<AccountListPreviewDto> filterAccounts(AccountFilterDto dto, Long userId) {
        CriteriaBuilder criteriaBuilder = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<AccountListPreviewDto> criteriaQuery = criteriaBuilder.createQuery(AccountListPreviewDto.class);
        Root<Account> root = criteriaQuery.from(Account.class);

        Predicate hidden = criteriaBuilder.equal(root.get("hidden"), false);

        Join<Account, User> productProductTranslateJoin = root.join("user");
        productProductTranslateJoin.on(criteriaBuilder.equal(productProductTranslateJoin.get("id"), userId));

        Predicate filterByCreatedDate = criteriaBuilder.conjunction();

        Predicate filterByDateRange = criteriaBuilder.conjunction();
        if (dto.toDate() > 0 && dto.fromDate() < dto.toDate()) {
            filterByDateRange = criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), dto.fromDate()),
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), dto.toDate()));
        }

        criteriaQuery.where(
                criteriaBuilder.and(
                        hidden,
                        filterByCreatedDate,
                        filterByDateRange
                ));

        criteriaQuery
                .select(criteriaBuilder.construct(
                        AccountListPreviewDto.class,
                        root.get("id"),
                        root.get("accountNumber"),
                        root.get("currency"),
                        root.get("cost")
                ));

        criteriaQuery.distinct(true);

        TypedQuery<AccountListPreviewDto> q = mEntityManager.createQuery(criteriaQuery);

        return q.getResultList();
    }

}
