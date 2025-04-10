package com.gmail.lgsc92.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigDecimal;

public class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().trim().replace(",", ".");
        return new BigDecimal(value);
    }
}