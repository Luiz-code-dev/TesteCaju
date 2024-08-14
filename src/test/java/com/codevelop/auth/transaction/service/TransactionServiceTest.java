package com.codevelop.auth.transaction.service;

import com.codevelop.auth.transaction.model.Account;
import com.codevelop.auth.transaction.model.Transaction;
import com.codevelop.auth.transaction.repository.AccountRepository;
import com.codevelop.auth.transaction.repository.MerchantMCCOverrideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MerchantMCCOverrideRepository merchantMCCOverrideRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransactionWithSufficientBalance() {
        // Configuração do cenário
        Account account = new Account();
        account.setId("123");
        account.setBalanceFood(new BigDecimal("100.00"));
        account.setBalanceMeal(new BigDecimal("50.00"));
        account.setBalanceCash(new BigDecimal("200.00"));

        Transaction transaction = new Transaction();
        transaction.setAccountId("123");
        transaction.setAmount(new BigDecimal("80.00"));
        transaction.setMcc("5411");  // Categoria FOOD
        transaction.setMerchant("SUPERMERCADO XYZ");

        when(accountRepository.findById("123")).thenReturn(Optional.of(account));
        when(merchantMCCOverrideRepository.findByMerchant("SUPERMERCADO XYZ")).thenReturn(Optional.empty());

        // Executa o teste
        String result = transactionService.processTransaction(transaction);

        // Verificações
        assertEquals("00", result);
        assertEquals(new BigDecimal("20.00"), account.getBalanceFood());
    }

    @Test
    public void testProcessTransactionWithInsufficientBalance() {
        // Configuração do cenário
        Account account = new Account();
        account.setId("123");
        account.setBalanceFood(new BigDecimal("50.00"));
        account.setBalanceMeal(new BigDecimal("0.00"));
        account.setBalanceCash(new BigDecimal("20.00"));

        Transaction transaction = new Transaction();
        transaction.setAccountId("123");
        transaction.setAmount(new BigDecimal("80.00"));
        transaction.setMcc("5411");  // Categoria FOOD
        transaction.setMerchant("SUPERMERCADO XYZ");

        when(accountRepository.findById("123")).thenReturn(Optional.of(account));
        when(merchantMCCOverrideRepository.findByMerchant("SUPERMERCADO XYZ")).thenReturn(Optional.empty());

        // Executa o teste
        String result = transactionService.processTransaction(transaction);

        // Verificações
        assertEquals("51", result);
    }

    @Test
    public void testProcessTransactionWithException() {
        // Configuração do cenário
        Transaction transaction = new Transaction();
        transaction.setAccountId("123");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setMcc("5411");
        transaction.setMerchant("SUPERMERCADO XYZ");

        // Simulando uma exceção ao buscar a conta
        when(accountRepository.findById("123")).thenThrow(new RuntimeException("Simulated Exception"));

        // Executa o teste
        String result = transactionService.processTransaction(transaction);

        // Verificações
        assertEquals("07", result);
    }
}