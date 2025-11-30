package com.bank.service;

import com.bank.model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class BankServiceTest {
    private BankService bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
    }

    @Test
    @DisplayName("Should create savings account")
    void testCreateSavingsAccount() {
        SavingsAccount account = bankService.createSavingsAccount("Alice", 1000.0);
        assertNotNull(account);
        assertEquals("Alice", account.getAccountHolderName());
        assertEquals(1, bankService.getAccountCount());
    }

    @Test
    @DisplayName("Should create checking account")
    void testCreateCheckingAccount() {
        CheckingAccount account = bankService.createCheckingAccount("Bob", 500.0);
        assertNotNull(account);
        assertEquals("Bob", account.getAccountHolderName());
    }

    @Test
    @DisplayName("Should retrieve account by number")
    void testGetAccount() {
        SavingsAccount account = bankService.createSavingsAccount("Charlie", 2000.0);
        Account retrieved = bankService.getAccount(account.getAccountNumber());
        assertEquals(account.getAccountNumber(), retrieved.getAccountNumber());
    }

    @Test
    @DisplayName("Should throw exception for non-existent account")
    void testGetNonExistentAccount() {
        assertThrows(IllegalArgumentException.class,
                () -> bankService.getAccount("INVALID123"));
    }

    @Test
    @DisplayName("Should get accounts by holder name")
    void testGetAccountsByHolder() {
        bankService.createSavingsAccount("David", 1000.0);
        bankService.createCheckingAccount("David", 500.0);

        List<Account> accounts = bankService.getAccountsByHolder("David");
        assertEquals(2, accounts.size());
    }

    @Test
    @DisplayName("Should calculate total deposits correctly")
    void testGetTotalDeposits() {
        bankService.createSavingsAccount("Eve", 1000.0);
        bankService.createCheckingAccount("Frank", 2000.0);

        double total = bankService.getTotalDeposits();
        assertEquals(3000.0, total);
    }

    @Test
    @DisplayName("Should delete account with zero balance")
    void testDeleteAccount() {
        CheckingAccount account = bankService.createCheckingAccount("Grace", 0);
        String accNum = account.getAccountNumber();

        bankService.deleteAccount(accNum);
        assertThrows(IllegalArgumentException.class,
                () -> bankService.getAccount(accNum));
    }

    @Test
    @DisplayName("Should not delete account with non-zero balance")
    void testDeleteAccountWithBalance() {
        SavingsAccount account = bankService.createSavingsAccount("Henry", 1000.0);
        assertThrows(IllegalStateException.class,
                () -> bankService.deleteAccount(account.getAccountNumber()));
}
}
