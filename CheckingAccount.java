package com.bank.model;

public class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500.0;
    private static final double OVERDRAFT_FEE = 35.0;

    public CheckingAccount(String accountHolderName, double initialDeposit) {
        super(accountHolderName, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > getBalance() + OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException("Withdrawal exceeds overdraft limit");
        }

        double balanceBefore = getBalance();
        super.withdraw(amount);

        // Apply overdraft fee if balance goes negative
        if (getBalance() < 0 && balanceBefore >= 0) {
            addTransaction(new Transaction(TransactionType.FEE, OVERDRAFT_FEE, "Overdraft fee"));
        }
    }

    @Override
    public String getAccountType() {
        return "Checking Account";
    }

    public static double getOverdraftLimit() { return OVERDRAFT_LIMIT; }
    public static double getOverdraftFee() { return OVERDRAFT_FEE;}
}
