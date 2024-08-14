package com.codevelop.auth.transaction.repository;

import com.codevelop.auth.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
