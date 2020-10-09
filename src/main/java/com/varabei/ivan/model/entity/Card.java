package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.util.List;

public class Card extends CardInfo implements Serializable {
    private List<Payment> payments;
    private AccountInfo accountInfo;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (payments != null ? !payments.equals(card.payments) : card.payments != null) return false;
        return accountInfo != null ? accountInfo.equals(card.accountInfo) : card.accountInfo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (accountInfo != null ? accountInfo.hashCode() : 0);
        return result;
    }
}
