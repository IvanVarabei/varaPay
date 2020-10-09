package com.varabei.ivan.model.entity;

import java.util.List;

public class Account extends AccountInfo {
    private User owner;

    private List<Card> cards;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (owner != null ? !owner.equals(account.owner) : account.owner != null) return false;
        return cards != null ? cards.equals(account.cards) : account.cards == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (cards != null ? cards.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append(", ownerId=").append(owner.getId());
        sb.append(", cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}