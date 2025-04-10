package com.gmail.lgsc92.utils.deserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FinancialBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Arredonda o valor para duas casas decimais utilizando HALF_UP
        BigDecimal rounded = value.setScale(2, RoundingMode.HALF_UP);
        
        // Se não houver parte fracionária (após remover zeros à direita), escreve como número
        if (rounded.stripTrailingZeros().scale() <= 0) {
            // Retira a escala para que seja um inteiro
            gen.writeNumber(rounded.intValue());
        } else {
            // Caso possua fração, formata com duas casas decimais e vírgula como separador
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
            gen.writeString(df.format(rounded));
        }
    }
}

