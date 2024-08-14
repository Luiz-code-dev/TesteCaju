package com.codevelop.auth.transaction.repository;

import com.codevelop.auth.transaction.model.MerchantMCCOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantMCCOverrideRepository extends JpaRepository<MerchantMCCOverride, String> {
    Optional<MerchantMCCOverride> findByMerchant(String merchant);
}