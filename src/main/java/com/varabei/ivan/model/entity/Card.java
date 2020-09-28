package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Card implements Serializable {
    private Long cardId;
    private Account account;
    private String cardNumber;
    private LocalDate validThruDate;
    private String cvc;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (cardId != null ? !cardId.equals(card.cardId) : card.cardId != null) return false;
        if (account != null ? !account.equals(card.account) : card.account != null) return false;
        if (cardNumber != null ? !cardNumber.equals(card.cardNumber) : card.cardNumber != null) return false;
        if (validThruDate != null ? !validThruDate.equals(card.validThruDate) : card.validThruDate != null)
            return false;
        return cvc != null ? cvc.equals(card.cvc) : card.cvc == null;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validThruDate != null ? validThruDate.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("cardId=").append(cardId);
        sb.append(", account=").append(account);
        sb.append(", cardNumber='").append(cardNumber).append('\'');
        sb.append(", validThruDate=").append(validThruDate);
        sb.append(", cvc='").append(cvc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
