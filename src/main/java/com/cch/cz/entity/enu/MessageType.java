package com.cch.cz.entity.enu;

public enum MessageType {
    END(0), RETAIN(1), GLOBAL(2);
    private int value;

    MessageType(int value) {
        this.value = value;
    }

    public static MessageType valueOf(int value) {
        switch (value) {
            case 0:
                return END;
            case 1:
                return RETAIN;
            case 2:
                return GLOBAL;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }
}
