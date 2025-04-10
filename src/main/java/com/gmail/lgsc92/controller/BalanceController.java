package com.gmail.lgsc92.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.lgsc92.model.dto.output.BalanceOutputDTO;
import com.gmail.lgsc92.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Balance API", description = "Endpoints para consulta de saldo")
@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountService accountService;

    @Operation(summary = "Consulta o saldo de uma conta")
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam("account_id") String accountId) {
        BalanceOutputDTO balanceDTO = accountService.getBalance(accountId);
        return ResponseEntity.ok(balanceDTO.getBalance());
    }
    
    @Operation(summary = "Reseta o estado das contas (para testes)")
    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        accountService.reset();
        return ResponseEntity.ok().body("OK");
    }
}
