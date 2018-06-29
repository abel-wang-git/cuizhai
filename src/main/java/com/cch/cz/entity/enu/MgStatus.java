package com.cch.cz.entity.enu;

public enum MgStatus {
    READ(0), NOREAD(1);
    private int value;

    MgStatus(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
