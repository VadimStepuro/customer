package com.stepuro.customer.model.enums;

public enum CardStatus implements TransferEntityStatus{
    ACTIVE,
    UNREACHABLE;

    @Override
    public boolean isActive() {
        return this.equals(ACTIVE);
    }
}
