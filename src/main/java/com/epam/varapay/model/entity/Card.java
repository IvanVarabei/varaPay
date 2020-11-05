package com.epam.varapay.model.entity;

import java.time.LocalDate;

public class Card extends Identifiable {
    private String cardNumber;
    private LocalDate validThru;
    private String cvc;
    private Account account;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Card card = (Card) o;
        if (cardNumber != null ? !cardNumber.equals(card.cardNumber) : card.cardNumber != null) {
            return false;
        }
        if (validThru != null ? !validThru.equals(card.validThru) : card.validThru != null) {
            return false;
        }
        if (cvc != null ? !cvc.equals(card.cvc) : card.cvc != null) return false;
        return account != null ? account.equals(card.account) : card.account == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validThru != null ? validThru.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("cardNumber='").append(cardNumber).append('\'');
        sb.append(", validThruDate=").append(validThru);
        sb.append(", cvc='").append(cvc).append('\'');
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
