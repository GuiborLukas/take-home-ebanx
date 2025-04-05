package com.gmail.lgsc92.model.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO genérico para eventos")
public record EventRequestDTO(
    @Schema(description = "Tipo do evento (deposit, withdraw, transfer)", example = "deposit", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("type") String type,
    
    @Schema(description = "Conta de origem (obrigatório para withdraw e transfer)", example = "100")
    @JsonProperty("origin") String origin,
    
    @Schema(description = "Conta de destino (obrigatório para deposit e transfer)", example = "200")
    @JsonProperty("destination") String destination,
    
    @Schema(description = "Valor da operação", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("amount") Integer amount
) {}
