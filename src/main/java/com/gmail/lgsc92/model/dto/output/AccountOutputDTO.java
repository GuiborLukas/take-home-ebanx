package com.gmail.lgsc92.model.dto.output;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.lgsc92.utils.deserializer.FinancialBigDecimalSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Dados de saída da conta")
public class AccountOutputDTO {
    @Schema(description = "Identificador da conta", example = "100")
    private String id;
    
    @Schema(description = "Saldo da conta", example = "20")
    @JsonSerialize(using = FinancialBigDecimalSerializer.class)
    private BigDecimal balance;
}
