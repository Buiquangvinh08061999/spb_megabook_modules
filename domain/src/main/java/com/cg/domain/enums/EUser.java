package com.cg.domain.enums;

public enum EUser {
    ADMIN("ADMIN"),
    STAFF("STAFF");

    private final String value;

    EUser(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }


}
