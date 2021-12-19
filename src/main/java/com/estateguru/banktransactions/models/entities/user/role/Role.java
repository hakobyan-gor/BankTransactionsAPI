package com.estateguru.banktransactions.models.entities.user.role;

import com.estateguru.banktransactions.models.entities.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Role extends AbstractEntity {

    @Column(name = "RoleName")
    private String roleName;

}