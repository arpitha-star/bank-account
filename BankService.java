package com.bank.service;

import com.bank.model.*;
import java.util.*;

public class BankService {
    private Map<String, Account> accounts;

    public BankService() {
        this.accounts = new HashMap<>();
    }

    public SavingsAccount createSavingsAccount(String holderName, double initialDeposit) {
        SavingsAccount account = new SavingsAccount(holderName, initialDeposit);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public CheckingAccount createCheckingAccount(String holderName, double initialDeposit) {
        CheckingAccount account = new CheckingAccount(holderName, initialDeposit);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        return account;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public List<Account> getAccountsByHolder(String holderName) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getAccountHolderName().equalsIgnoreCase(holderName)) {
                result.add(account);
            }
        }
        return result;
    }

    public void deleteAccount(String accountNumber) {
        Account account = getAccount(accountNumber);
        if (account.getBalance() != 0) {
            throw new IllegalStateException("Cannot delete account with non-zero balance");
        }
        accounts.remove(accountNumber);
    }

    public double getTotalDeposits() {
        return accounts.values().stream()
                .mapToDouble(Account::getBalance)
                .filter(balance -> balance > 0)
                .sum();
    }

    public int getAccountCount() {
        return accounts.size();
}
}
