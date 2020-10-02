package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Account implements Serializable {
    private Long accountId;
    private User owner;
    private BigDecimal balance;
    private boolean isActive;
    private List<Card> cards;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("accountId=").append(accountId);
        sb.append(", ownerId=").append(owner.getUserId());
        sb.append(", balance=").append(balance);
        sb.append(", isActive=").append(isActive);
        sb.append(", cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}