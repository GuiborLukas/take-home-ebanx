package com.gmail.lgsc92.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.lgsc92.model.dto.input.EventRequestDTO;
import com.gmail.lgsc92.model.dto.output.EventOutputDTO;
import com.gmail.lgsc92.service.EventServiceContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Event API", description = "Endpoints para processamento de eventos")
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceContext eventServiceContext;

    @Operation(summary = "Processa um evento de conta")
    @PostMapping
    public ResponseEntity<EventOutputDTO> processEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO) {
        EventOutputDTO output = eventServiceContext.processEvent(eventRequestDTO);
        return ResponseEntity.status(201).body(output);
    }

}
