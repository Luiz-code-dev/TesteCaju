
CREATE TABLE ACCOUNT (
    id VARCHAR(255) PRIMARY KEY,
    balance_cash DECIMAL(19, 2),
    balance_food DECIMAL(19, 2),
    balance_meal DECIMAL(19, 2)
);

CREATE TABLE TRANSACTION (
    id VARCHAR(255) PRIMARY KEY,
    account_id VARCHAR(255),
    amount DECIMAL(19, 2),
    merchant VARCHAR(255),
    mcc VARCHAR(4),
    FOREIGN KEY (account_id) REFERENCES ACCOUNT(id)
);

CREATE TABLE MERCHANT_MCC_OVERRIDE (
    merchant VARCHAR(255) PRIMARY KEY,
    overridden_mcc VARCHAR(4)
);


-- Inserir dados na tabela ACCOUNT
INSERT INTO ACCOUNT (id, balance_cash, balance_food, balance_meal) VALUES ('123', 1000.00, 500.00, 300.00);

-- Inserir dados na tabela TRANSACTION
INSERT INTO TRANSACTION (id, account_id, amount, merchant, mcc) VALUES ('1', '123', 100.00, 'PADARIA DO ZE', '5811');

-- Inserir dados na tabela MERCHANT_MCC_OVERRIDE
INSERT INTO MERCHANT_MCC_OVERRIDE (merchant, overridden_mcc) VALUES ('UBER EATS SAO PAULO BR', '5812');


UPDATE MERCHANT_MCC_OVERRIDE
SET merchant = 'UBER EATS                   SAO PAULO BR'
WHERE merchant = 'UBER EATS SAO PAULO BR';
