package com.gmail.lgsc92.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Dados de saída para a consulta de saldo")
public class BalanceOutputDTO {
    @Schema(description = "Saldo disponível", example = "20")
    private Integer balance;
}
