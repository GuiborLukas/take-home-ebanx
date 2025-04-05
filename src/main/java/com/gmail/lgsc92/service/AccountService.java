package com.gmail.lgsc92.service;

import com.gmail.lgsc92.model.dto.input.DepositEventInputDTO;
import com.gmail.lgsc92.model.dto.input.TransferEventInputDTO;
import com.gmail.lgsc92.model.dto.input.WithdrawEventInputDTO;
import com.gmail.lgsc92.model.dto.output.BalanceOutputDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;

public interface AccountService {
    EventOutputDTO processDeposit(DepositEventInputDTO depositDTO);
    EventOutputDTO processWithdraw(WithdrawEventInputDTO withdrawDTO);
    EventOutputDTO processTransfer(TransferEventInputDTO transferDTO);
    BalanceOutputDTO getBalance(String accountId);
    void reset();
}
