package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Card  extends CardInfo implements Serializable{
    private List<Payment> payments;
    private Account account;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append(", account=").append(account);
        sb.append("payments=").append(payments);
        sb.append('}');
        return sb.toString();
    }
}
