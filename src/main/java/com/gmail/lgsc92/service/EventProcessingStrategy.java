package com.gmail.lgsc92.service;

import com.gmail.lgsc92.model.dto.input.EventRequestDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;

public interface EventProcessingStrategy {
    EventOutputDTO process(EventRequestDTO genericDTO);
}
