package com.cch.cz.entity.enu;

public enum MessageTyoe {
    END(0), RETAIN(1), GLOBAL(2);
    private int value;

    MessageTyoe(int value) {
        this.value = value;
    }

    public static MessageTyoe valueOf(int value) {
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
