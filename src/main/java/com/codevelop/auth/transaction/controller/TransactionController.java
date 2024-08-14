package com.codevelop.auth.transaction.controller;

import com.codevelop.auth.transaction.model.Transaction;
import com.codevelop.auth.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> processTransaction(@RequestBody Transaction transaction) {
        try {
            String responseCode = transactionService.processTransaction(transaction);
            return ResponseEntity.ok("{ \"code\": \"" + responseCode + "\" }");
        } catch (Exception e) {
            return ResponseEntity.ok("{ \"code\": \"07\" }");  // Erro genérico
        }
    }
}
