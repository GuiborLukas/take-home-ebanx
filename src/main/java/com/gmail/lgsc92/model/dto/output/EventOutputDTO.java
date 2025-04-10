package com.gmail.lgsc92.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Dados de saída para o processamento de eventos")
public class EventOutputDTO {
    @Schema(description = "Dados da conta de origem, se aplicável")
    private AccountOutputDTO origin;
    
    @Schema(description = "Dados da conta de destino, se aplicável")
    private AccountOutputDTO destination;
}
