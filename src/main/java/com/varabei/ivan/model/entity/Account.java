package com.varabei.ivan.model.entity;

import java.math.BigDecimal;

public class Account extends Identifiable {
    private BigDecimal balance;
    private boolean isActive;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Account account = (Account) o;
        if (isActive != account.isActive) {
            return false;
        }
        if (balance != null ? !balance.equals(account.balance) : account.balance != null) {
            return false;
        }
        return user != null ? user.equals(account.user) : account.user == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("balance=").append(balance);
        sb.append(", isActive=").append(isActive);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
