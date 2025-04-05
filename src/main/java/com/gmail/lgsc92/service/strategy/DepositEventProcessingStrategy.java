package com.gmail.lgsc92.service.strategy;

import com.gmail.lgsc92.mapper.EventMapper;
import com.gmail.lgsc92.model.dto.input.DepositEventInputDTO;
import com.gmail.lgsc92.model.dto.input.EventRequestDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;
import com.gmail.lgsc92.service.AccountService;
import com.gmail.lgsc92.service.EventProcessingStrategy;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DepositEventProcessingStrategy implements EventProcessingStrategy {

    private final AccountService accountService;
    private final EventMapper eventMapper;
    private final Validator validator;

    public DepositEventProcessingStrategy(AccountService accountService, EventMapper eventMapper) {
        this.accountService = accountService;
        this.eventMapper = eventMapper;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public EventOutputDTO process(EventRequestDTO genericDTO) {
        DepositEventInputDTO depositDTO = eventMapper.toDeposit(genericDTO);
        Set<ConstraintViolation<DepositEventInputDTO>> violations = validator.validate(depositDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return accountService.processDeposit(depositDTO);
    }
}
