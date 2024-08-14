package com.codevelop.auth.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal balanceCash;
    private BigDecimal balanceFood;
    private BigDecimal balanceMeal;

}
