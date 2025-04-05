package com.gmail.lgsc92.mapper;

import org.springframework.stereotype.Component;

import com.gmail.lgsc92.model.dto.input.DepositEventInputDTO;
import com.gmail.lgsc92.model.dto.input.EventRequestDTO;
import com.gmail.lgsc92.model.dto.input.TransferEventInputDTO;
import com.gmail.lgsc92.model.dto.input.WithdrawEventInputDTO;

@Component
public class EventMapper {

	public static DepositEventInputDTO toDeposit(EventRequestDTO eventRequestDTO) {
        return new DepositEventInputDTO(
            eventRequestDTO.destination(),
            eventRequestDTO.amount()
        );
    }

    public static WithdrawEventInputDTO toWithdraw(EventRequestDTO eventRequestDTO) {
        return new WithdrawEventInputDTO(
            eventRequestDTO.origin(),
            eventRequestDTO.amount()
        );
    }

    public static TransferEventInputDTO toTransfer(EventRequestDTO eventRequestDTO) {
        return new TransferEventInputDTO(
            eventRequestDTO.origin(),
            eventRequestDTO.amount(),
            eventRequestDTO.destination()
        );
    }
}
