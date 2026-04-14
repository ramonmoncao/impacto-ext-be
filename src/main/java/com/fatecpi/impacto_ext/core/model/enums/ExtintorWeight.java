package com.fatecpi.impacto_ext.core.model.enums;

public enum ExtintorWeight {
    KG_4(4),
    KG_6(6),
    KG_8(8),
    KG_10(10),
    KG_12(12);

    private final int value;

    ExtintorWeight(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
