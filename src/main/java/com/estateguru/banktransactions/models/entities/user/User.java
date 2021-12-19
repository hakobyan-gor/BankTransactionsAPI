package com.estateguru.banktransactions.models.entities.user;

import com.estateguru.banktransactions.models.entities.AbstractEntity;
import com.estateguru.banktransactions.models.entities.account.Account;
import com.estateguru.banktransactions.models.entities.user.role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class User extends AbstractEntity {

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RoleId")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Account> accounts;

}