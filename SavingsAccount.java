package com.bank.model;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.04; // 4% annual
    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(String accountHolderName, double initialDeposit) {
        super(accountHolderName, initialDeposit);
        if (initialDeposit < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Savings account requires minimum initial deposit of " + MINIMUM_BALANCE);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < MINIMUM_BALANCE) {
            throw new InsufficientFundsException("Withdrawal would violate minimum balance requirement of " + MINIMUM_BALANCE);
        }
        super.withdraw(amount);
    }

    public void addInterest() {
        double interest = getBalance() * INTEREST_RATE / 12; // Monthly interest
        deposit(interest);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    public static double getInterestRate() { return INTEREST_RATE; }
    public static double getMinimumBalance() { return MINIMUM_BALANCE;}
}
