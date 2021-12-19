package com.estateguru.banktransactions.specifications;

import com.estateguru.banktransactions.models.dtos.user.UserListPreviewDto;
import com.estateguru.banktransactions.models.entities.user.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class UserSpecification {

    @Lazy
    private final EntityManager mEntityManager;

    public UserSpecification(EntityManager entityManager) {
        mEntityManager = entityManager;
    }

    public List<UserListPreviewDto> getUsers(int page, int size) {
        CriteriaBuilder criteriaBuilder = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<UserListPreviewDto> criteriaQuery = criteriaBuilder.createQuery(UserListPreviewDto.class);
        Root<User> root = criteriaQuery.from(User.class);

        Predicate hidden = criteriaBuilder.equal(root.get("hidden"), false);

        criteriaQuery.where(criteriaBuilder.and(hidden));

        criteriaQuery
                .select(criteriaBuilder.construct(
                        UserListPreviewDto.class,
                        root.get("firstName"),
                        root.get("createdDate"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("id")
                ));

        criteriaQuery.distinct(true);

        TypedQuery<UserListPreviewDto> q = mEntityManager.createQuery(criteriaQuery);
        q.setFirstResult((page - 1) * size);
        q.setMaxResults(size);

        return q.getResultList();
    }

    public long countOfUsers() {
        CriteriaBuilder criteriaBuilder = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Long> count = criteriaBuilder.createQuery(Long.class);
        Root<User> root = count.from(User.class);
        Predicate hidden = criteriaBuilder.equal(root.get("hidden"), false);
        return mEntityManager.createQuery(count.select(criteriaBuilder.count(count.from(User.class))).where(hidden)).getSingleResult();
    }

}
