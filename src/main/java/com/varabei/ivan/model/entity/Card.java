package com.varabei.ivan.model.entity;

import java.time.LocalDate;

public class Card extends Identifiable {
    private String cardNumber;
    private LocalDate validThruDate;
    private String cvc;
    private Account account;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getValidThruDate() {
        return validThruDate;
    }

    public void setValidThruDate(LocalDate validThruDate) {
        this.validThruDate = validThruDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (cardNumber != null ? !cardNumber.equals(card.cardNumber) : card.cardNumber != null) return false;
        if (validThruDate != null ? !validThruDate.equals(card.validThruDate) : card.validThruDate != null)
            return false;
        if (cvc != null ? !cvc.equals(card.cvc) : card.cvc != null) return false;
        return account != null ? account.equals(card.account) : card.account == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validThruDate != null ? validThruDate.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("cardNumber='").append(cardNumber).append('\'');
        sb.append(", validThruDate=").append(validThruDate);
        sb.append(", cvc='").append(cvc).append('\'');
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
