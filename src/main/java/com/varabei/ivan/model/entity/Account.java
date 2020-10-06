package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Account extends StorableItem implements Serializable {
    private User owner;
    private BigDecimal balance;
    private boolean isActive;
    private List<Card> cards;

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
        if (owner != null ? !owner.equals(account.owner) : account.owner != null) {
            return false;
        }
        if (balance != null ? !balance.equals(account.balance) : account.balance != null) {
            return false;
        }
        return cards != null ? cards.equals(account.cards) : account.cards == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append(", ownerId=").append(owner.getId());
        sb.append(", balance=").append(balance);
        sb.append(", isActive=").append(isActive);
        sb.append(", cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}