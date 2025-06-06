package com.gmail.lgsc92.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gmail.lgsc92.exception.AccountNotFoundException;
import com.gmail.lgsc92.model.dto.input.DepositEventInputDTO;
import com.gmail.lgsc92.model.dto.input.TransferEventInputDTO;
import com.gmail.lgsc92.model.dto.input.WithdrawEventInputDTO;
import com.gmail.lgsc92.model.dto.output.BalanceOutputDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;
import com.gmail.lgsc92.repository.AccountRepository;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        AccountRepository accountRepository = new AccountRepository();
        accountService = new AccountServiceImpl(accountRepository);
        accountService.reset();
    }

    @Test
    void testReset() {
        // Cria a conta com depósito inicial
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(50));
        accountService.processDeposit(depositDTO);

        BalanceOutputDTO balanceBeforeReset = accountService.getBalance("100");
        assertEquals(BigDecimal.valueOf(50), balanceBeforeReset.getBalance());

        accountService.reset();

        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class, () -> {
            accountService.getBalance("100");
        });
        assertTrue(ex.getMessage().contains("Conta 100 não encontrada"));
    }

    @Test
    void testGetBalanceForNonExistingAccount() {
        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class, () -> {
            accountService.getBalance("1234");
        });
        assertTrue(ex.getMessage().contains("Conta 1234 não encontrada"));
    }

    @Test
    void testDepositCreatesAccount() {
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(10));
        EventOutputDTO output = accountService.processDeposit(depositDTO);
        assertNotNull(output.getDestination());
        assertEquals("100", output.getDestination().getId());
        assertEquals(BigDecimal.valueOf(10), output.getDestination().getBalance());
    }

    @Test
    void testDepositIntoExistingAccount() {
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(10));
        accountService.processDeposit(depositDTO);
        EventOutputDTO output = accountService.processDeposit(depositDTO);
        assertEquals("100", output.getDestination().getId());
        assertEquals(BigDecimal.valueOf(20), output.getDestination().getBalance());
    }

    @Test
    void testGetBalanceForExistingAccount() {
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(10));
        accountService.processDeposit(depositDTO);
        BalanceOutputDTO balanceDTO = accountService.getBalance("100");
        assertEquals(BigDecimal.valueOf(10), balanceDTO.getBalance());
    }

    @Test
    void testWithdrawFromNonExistingAccount() {
        WithdrawEventInputDTO withdrawDTO = new WithdrawEventInputDTO("200", BigDecimal.valueOf(10));
        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class, () -> {
            accountService.processWithdraw(withdrawDTO);
        });
        assertTrue(ex.getMessage().contains("Conta 200 não encontrada"));
    }

    @Test
    void testWithdrawFromExistingAccount() {
        // Cria a conta com depósito inicial
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(10));
        accountService.processDeposit(depositDTO);
        // Realiza o saque
        WithdrawEventInputDTO withdrawDTO = new WithdrawEventInputDTO("100", BigDecimal.valueOf(5));
        EventOutputDTO output = accountService.processWithdraw(withdrawDTO);
        assertNotNull(output.getOrigin());
        assertEquals("100", output.getOrigin().getId());
        assertEquals(BigDecimal.valueOf(5), output.getOrigin().getBalance());
    }

    @Test
    void testTransferFromExistingAccount() {
        // Cria a conta de origem com depósito inicial
        DepositEventInputDTO depositDTO = new DepositEventInputDTO("100", BigDecimal.valueOf(20));
        accountService.processDeposit(depositDTO);
        // Realiza a transferência
        TransferEventInputDTO transferDTO = new TransferEventInputDTO("100", BigDecimal.valueOf(15), "300");
        EventOutputDTO output = accountService.processTransfer(transferDTO);
        assertNotNull(output.getOrigin());
        assertNotNull(output.getDestination());
        assertEquals("100", output.getOrigin().getId());
        assertEquals(BigDecimal.valueOf(5), output.getOrigin().getBalance());
        assertEquals("300", output.getDestination().getId());
        assertEquals(BigDecimal.valueOf(15), output.getDestination().getBalance());
    }

    @Test
    void testTransferFromNonExistingAccount() {
        TransferEventInputDTO transferDTO = new TransferEventInputDTO("200", BigDecimal.valueOf(15), "300");
        AccountNotFoundException ex = assertThrows(AccountNotFoundException.class, () -> {
            accountService.processTransfer(transferDTO);
        });
        assertTrue(ex.getMessage().contains("Conta 200 não encontrada"));
    }
}
