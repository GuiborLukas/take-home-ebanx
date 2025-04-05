package com.gmail.lgsc92.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para evento de saque")
public record WithdrawEventInputDTO(
    @NotBlank(message = "Origem é obrigatória")
    @Schema(description = "Conta de origem", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    String origin,
    
    @NotNull(message = "Valor é obrigatório")
    @Min(value = 1, message = "Valor deve ser maior que zero")
    @Schema(description = "Valor a sacar", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer amount
) {}
