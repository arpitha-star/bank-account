package com.bank.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<Transaction> transactionHistory;
    private LocalDateTime createdDate;
    private boolean isActive;

    public Account(String accountHolderName, double initialDeposit) {
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }

        this.accountNumber = "ACC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
        this.isActive = true;

        if (initialDeposit > 0) {
            addTransaction(new Transaction(TransactionType.DEPOSIT, initialDeposit, "Initial deposit"));
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }

        balance += amount;
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount, "Deposit"));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds. Available balance: " + balance);
        }

        balance -= amount;
        addTransaction(new Transaction(TransactionType.WITHDRAWAL, amount, "Withdrawal"));
    }

    public void transfer(Account targetAccount, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        if (!targetAccount.isActive()) {
            throw new IllegalStateException("Target account is not active");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        this.balance -= amount;
        targetAccount.balance += amount;

        addTransaction(new Transaction(TransactionType.TRANSFER_OUT, amount,
                "Transfer to " + targetAccount.getAccountNumber()));
        targetAccount.addTransaction(new Transaction(TransactionType.TRANSFER_IN, amount,
                "Transfer from " + this.accountNumber));
    }

    protected void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void closeAccount() {
        if (balance > 0) {
            throw new IllegalStateException("Cannot close account with positive balance");
        }
        this.isActive = false;
    }

    public abstract String getAccountType();

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactionHistory() { return new ArrayList<>(transactionHistory); }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public boolean isActive() { return isActive;}
}
