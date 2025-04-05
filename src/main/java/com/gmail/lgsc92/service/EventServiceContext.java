package com.gmail.lgsc92.service;

import com.gmail.lgsc92.model.dto.input.EventRequestDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;
import com.gmail.lgsc92.service.strategy.DepositEventProcessingStrategy;
import com.gmail.lgsc92.service.strategy.TransferEventProcessingStrategy;
import com.gmail.lgsc92.service.strategy.WithdrawEventProcessingStrategy;
import com.gmail.lgsc92.utils.enums.EventType;
import org.springframework.stereotype.Service;

@Service
public class EventServiceContext {

    private final EventProcessingStrategy depositStrategy;
    private final EventProcessingStrategy withdrawStrategy;
    private final EventProcessingStrategy transferStrategy;

    public EventServiceContext(DepositEventProcessingStrategy depositStrategy,
                               WithdrawEventProcessingStrategy withdrawStrategy,
                               TransferEventProcessingStrategy transferStrategy) {
        this.depositStrategy = depositStrategy;
        this.withdrawStrategy = withdrawStrategy;
        this.transferStrategy = transferStrategy;
    }

    public EventOutputDTO processEvent(EventRequestDTO eventRequestDTO) {
        EventType eventType = EventType.from(eventRequestDTO.type());
        return switch (eventType) {
            case DEPOSIT -> depositStrategy.process(eventRequestDTO);
            case WITHDRAW -> withdrawStrategy.process(eventRequestDTO);
            case TRANSFER -> transferStrategy.process(eventRequestDTO);
        };
    }
}
