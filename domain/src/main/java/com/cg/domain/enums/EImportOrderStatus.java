package com.cg.domain.enums;

public enum EImportOrderStatus {
    PENDING("PENDING"), //Chờ nhập
    COMPLETED("COMPLETED"),
    CANCEL("CANCEL"); // Hoàn thành

    private final String value;

    EImportOrderStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
