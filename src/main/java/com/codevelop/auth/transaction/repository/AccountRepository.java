package com.codevelop.auth.transaction.repository;

import com.codevelop.auth.transaction.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
