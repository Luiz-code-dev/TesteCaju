package com.codevelop.auth.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "MERCHANT_MCC_OVERRIDE")
public class MerchantMCCOverride {

    @Id
    private String merchant;
    private String overriddenMcc;

}
