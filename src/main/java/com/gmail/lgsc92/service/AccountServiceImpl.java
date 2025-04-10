package com.gmail.lgsc92.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.gmail.lgsc92.exception.AccountNotFoundException;
import com.gmail.lgsc92.exception.InsufficientFundsException;
import com.gmail.lgsc92.model.dto.input.DepositEventInputDTO;
import com.gmail.lgsc92.model.dto.input.TransferEventInputDTO;
import com.gmail.lgsc92.model.dto.input.WithdrawEventInputDTO;
import com.gmail.lgsc92.model.dto.output.AccountOutputDTO;
import com.gmail.lgsc92.model.dto.output.BalanceOutputDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;
import com.gmail.lgsc92.model.entity.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final Map<String, Account> accountStore = new ConcurrentHashMap<>();

    @Override
    public EventOutputDTO processDeposit(DepositEventInputDTO depositDTO) {
        Account account = accountStore.compute(depositDTO.destination(), (key, acc) -> {
            if (acc == null) {
                return Account.builder()
                    .id(depositDTO.destination())
                    .balance(depositDTO.amount())
                    .build();
            }
            acc.setBalance(acc.getBalance().add(depositDTO.amount()));
            return acc;
        });
        return EventOutputDTO.builder()
                .destination(AccountOutputDTO.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .build())
                .build();
    }

    @Override
    public EventOutputDTO processWithdraw(WithdrawEventInputDTO withdrawDTO) {
        Account account = accountStore.get(withdrawDTO.origin());
        if (account == null) {
            throw new AccountNotFoundException("Conta " + withdrawDTO.origin() + " não encontrada");
        }
        if (account.getBalance().compareTo(withdrawDTO.amount()) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para saque");
        }
        account.setBalance(account.getBalance().subtract(withdrawDTO.amount()));
        return EventOutputDTO.builder()
                .origin(AccountOutputDTO.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .build())
                .build();
    }

    @Override
    public EventOutputDTO processTransfer(TransferEventInputDTO transferDTO) {
        Account originAccount = accountStore.get(transferDTO.origin());
        if (originAccount == null) {
            throw new AccountNotFoundException("Conta " + transferDTO.origin() + " não encontrada");
        }
        if (originAccount.getBalance().compareTo(transferDTO.amount()) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para transferência");
        }
        originAccount.setBalance(originAccount.getBalance().subtract(transferDTO.amount()));

        Account destAccount = accountStore.compute(transferDTO.destination(), (key, acc) -> {
            if (acc == null) {
                return Account.builder()
                        .id(transferDTO.destination())
                        .balance(transferDTO.amount())
                        .build();
            }
            acc.setBalance(acc.getBalance().add(transferDTO.amount()));
            return acc;
        });
        return EventOutputDTO.builder()
                .origin(AccountOutputDTO.builder()
                        .id(originAccount.getId())
                        .balance(originAccount.getBalance())
                        .build())
                .destination(AccountOutputDTO.builder()
                        .id(destAccount.getId())
                        .balance(destAccount.getBalance())
                        .build())
                .build();
    }

    @Override
    public BalanceOutputDTO getBalance(String accountId) {
        Account account = accountStore.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Conta " + accountId + " não encontrada");
        }
        return BalanceOutputDTO.builder()
                .balance(account.getBalance())
                .build();
    }

    @Override
    public void reset() {
        accountStore.clear();
    }
}
