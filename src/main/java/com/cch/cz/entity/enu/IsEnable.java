package com.cch.cz.entity.enu;

public enum IsEnable {
    ENABLE(0), DISENABLE(1);
    private int value;

    IsEnable(int value) {
        this.value = value;
    }

    public static IsEnable valueOf(int value) {
        switch (value) {
            case 0:
                return ENABLE;
            case 1:
                return DISENABLE;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }
}


