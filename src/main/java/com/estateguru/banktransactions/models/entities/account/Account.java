package com.estateguru.banktransactions.models.entities.account;

import com.estateguru.banktransactions.models.entities.AbstractEntity;
import com.estateguru.banktransactions.models.entities.user.User;
import com.estateguru.banktransactions.models.enums.CurrencyEnum;
import lombok.*;

import javax.persistence.*;

@Table(name = "Accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Account extends AbstractEntity {

    @JoinColumn(name = "UserId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "Currency")
    @Enumerated
    private CurrencyEnum currency;

    @Column(name = "Cost")
    private double cost;

    @Column(name = "AccountNumber", columnDefinition = "varchar(50)")
    private String accountNumber;

}