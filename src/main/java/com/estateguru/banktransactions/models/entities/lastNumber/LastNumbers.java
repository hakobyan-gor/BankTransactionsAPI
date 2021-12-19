package com.estateguru.banktransactions.models.entities.lastNumber;

import com.estateguru.banktransactions.models.entities.AbstractEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "LastNumbers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class LastNumbers extends AbstractEntity {

    @Column(name = "AccountLastNumber")
    private int accountLastNumber;

}