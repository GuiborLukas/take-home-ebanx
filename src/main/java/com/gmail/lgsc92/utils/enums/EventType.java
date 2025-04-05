package com.gmail.lgsc92.utils.enums;

public enum EventType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public static EventType from(String type) {
        for (EventType t : EventType.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de evento inválido: " + type);
    }
}
