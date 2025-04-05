package com.gmail.lgsc92.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para evento de depósito")
public record DepositEventInputDTO(
    @NotBlank(message = "Destino é obrigatório")
    @Schema(description = "Conta de destino", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    String destination,
    
    @NotNull(message = "Valor é obrigatório")
    @Min(value = 1, message = "Valor deve ser maior que zero")
    @Schema(description = "Valor a depositar", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer amount
) {}
