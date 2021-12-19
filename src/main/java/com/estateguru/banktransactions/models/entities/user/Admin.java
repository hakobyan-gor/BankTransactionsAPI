package com.estateguru.banktransactions.models.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Admin extends User {
}