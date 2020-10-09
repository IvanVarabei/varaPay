package com.varabei.ivan.model.entity;

import java.math.BigDecimal;

public class AccountInfo extends StorableItem{
    private BigDecimal balance;
    private boolean isActive;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AccountInfo that = (AccountInfo) o;

        if (isActive != that.isActive) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountInfo{");
        sb.append("balance=").append(balance);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
