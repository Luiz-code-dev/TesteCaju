package com.codevelop.auth.transaction.service;

import com.codevelop.auth.transaction.model.Account;
import com.codevelop.auth.transaction.model.Transaction;
import com.codevelop.auth.transaction.model.MerchantMCCOverride;
import com.codevelop.auth.transaction.repository.AccountRepository;
import com.codevelop.auth.transaction.repository.MerchantMCCOverrideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MerchantMCCOverrideRepository merchantMCCOverrideRepository;

    @Transactional
    public String processTransaction(Transaction transaction) {
        try {
            logger.info("Processando transação para Account ID: {}", transaction.getAccountId());

            String mcc = getMCC(transaction);
            logger.info("MCC determinado: {}", mcc);

            Account account = accountRepository.findById(transaction.getAccountId())
                    .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));

            BigDecimal amount = transaction.getAmount();
            String responseCode;

            switch (mcc) {
                case "5411":
                case "5412":
                    responseCode = processAmount(account, amount, "FOOD");
                    break;
                case "5811":
                case "5812":
                    responseCode = processAmount(account, amount, "MEAL");
                    break;
                default:
                    responseCode = processAmount(account, amount, "CASH");
                    break;
            }

            if (responseCode.equals("00")) {
                accountRepository.save(account);
            }

            return responseCode;

        } catch (Exception e) {
            logger.error("Erro ao processar transação: ", e);
            return "07";
        }
    }

    private String getMCC(Transaction transaction) {
        return merchantMCCOverrideRepository.findByMerchant(transaction.getMerchant())
                .map(MerchantMCCOverride::getOverriddenMcc)
                .orElse(transaction.getMcc());
    }

    private String processAmount(Account account, BigDecimal amount, String category) {
        BigDecimal balance = getBalance(account, category);

        if (balance.compareTo(amount) >= 0) {
            updateBalance(account, category, balance.subtract(amount));
            return "00";  // Transação aprovada
        } else if (!category.equals("CASH")) {
            return processAmount(account, amount, "CASH");
        } else {
            return "51";  // Saldo insuficiente
        }
    }

    private BigDecimal getBalance(Account account, String category) {
        switch (category) {
            case "FOOD":
                return account.getBalanceFood();
            case "MEAL":
                return account.getBalanceMeal();
            case "CASH":
            default:
                return account.getBalanceCash();
        }
    }

    private void updateBalance(Account account, String category, BigDecimal newBalance) {
        switch (category) {
            case "FOOD":
                account.setBalanceFood(newBalance);
                break;
            case "MEAL":
                account.setBalanceMeal(newBalance);
                break;
            case "CASH":
                account.setBalanceCash(newBalance);
                break;
        }
    }
}