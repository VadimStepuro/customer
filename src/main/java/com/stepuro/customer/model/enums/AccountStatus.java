package com.stepuro.customer.model.enums;

public enum AccountStatus implements TransferEntityStatus{
    ACTIVE,
    CLOSED;

    @Override
    public boolean isActive() {
        return this.equals(ACTIVE);
    }
}
