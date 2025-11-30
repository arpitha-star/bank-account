package com.bank.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() {
        savingsAccount = new SavingsAccount("John Doe", 1000.0);
        checkingAccount = new CheckingAccount("Jane Smith", 500.0);
    }

    @Test
    @DisplayName("Should create account with valid initial deposit")
    void testAccountCreation() {
        assertNotNull(savingsAccount.getAccountNumber());
        assertEquals("John Doe", savingsAccount.getAccountHolderName());
        assertEquals(1000.0, savingsAccount.getBalance());
        assertTrue(savingsAccount.isActive());
    }

    @Test
    @DisplayName("Should throw exception for empty account holder name")
    void testEmptyAccountHolderName() {
        assertThrows(IllegalArgumentException.class,
                () -> new SavingsAccount("", 1000.0));
    }

    @Test
    @DisplayName("Should deposit amount successfully")
    void testDeposit() {
        savingsAccount.deposit(500.0);
        assertEquals(1500.0, savingsAccount.getBalance());
    }

    @Test
    @DisplayName("Should throw exception for negative deposit")
    void testNegativeDeposit() {
        assertThrows(IllegalArgumentException.class,
                () -> savingsAccount.deposit(-100.0));
    }

    @Test
    @DisplayName("Should withdraw amount successfully")
    void testWithdrawal() {
        checkingAccount.withdraw(200.0);
        assertEquals(300.0, checkingAccount.getBalance());
    }

    @Test
    @DisplayName("Should throw exception for insufficient funds")
    void testInsufficientFunds() {
        assertThrows(InsufficientFundsException.class,
                () -> savingsAccount.withdraw(2000.0));
    }

    @Test
    @DisplayName("Should transfer amount between accounts")
    void testTransfer() {
        savingsAccount.transfer(checkingAccount, 300.0);
        assertEquals(700.0, savingsAccount.getBalance());
        assertEquals(800.0, checkingAccount.getBalance());
    }

    @Test
    @DisplayName("Should maintain transaction history")
    void testTransactionHistory() {
        savingsAccount.deposit(200.0);
        savingsAccount.withdraw(100.0);
        assertEquals(3, savingsAccount.getTransactionHistory().size());
    }

    @Test
    @DisplayName("Should enforce minimum balance for savings account")
    void testSavingsMinimumBalance() {
        assertThrows(InsufficientFundsException.class,
                () -> savingsAccount.withdraw(600.0));
    }

    @Test
    @DisplayName("Should close account with zero balance")
    void testCloseAccount() {
        CheckingAccount acc = new CheckingAccount("Test", 0);
        acc.closeAccount();
        assertFalse(acc.isActive());
}
}
