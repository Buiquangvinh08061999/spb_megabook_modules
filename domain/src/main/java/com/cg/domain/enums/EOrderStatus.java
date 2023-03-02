package com.cg.domain.enums;

public enum EOrderStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCEL("CANCEL");

    private final String value;

    EOrderStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
