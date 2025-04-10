package com.gmail.lgsc92.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gmail.lgsc92.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Reset API", description = "Endpoint para resetar o estado das contas (para testes)")
@RestController
@RequestMapping("/reset")
@RequiredArgsConstructor
public class ResetController {

    private final AccountService accountService;

    @Operation(summary = "Reseta o estado das contas")
    @PostMapping
    public ResponseEntity<String> reset() {
        accountService.reset();
        return ResponseEntity.ok("OK");
    }
}
