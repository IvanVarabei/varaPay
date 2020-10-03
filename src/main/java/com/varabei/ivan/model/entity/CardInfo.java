package com.varabei.ivan.model.entity;

import java.time.LocalDate;

public class CardInfo {
    private Long cardId;
    private String cardNumber;
    private LocalDate validThruDate;
    private String cvc;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
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

        CardInfo cardInfo = (CardInfo) o;

        if (cardId != null ? !cardId.equals(cardInfo.cardId) : cardInfo.cardId != null) return false;
        if (cardNumber != null ? !cardNumber.equals(cardInfo.cardNumber) : cardInfo.cardNumber != null) return false;
        if (validThruDate != null ? !validThruDate.equals(cardInfo.validThruDate) : cardInfo.validThruDate != null)
            return false;
        return cvc != null ? cvc.equals(cardInfo.cvc) : cardInfo.cvc == null;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validThruDate != null ? validThruDate.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardInfo{");
        sb.append("cardId=").append(cardId);
        sb.append(", cardNumber='").append(cardNumber).append('\'');
        sb.append(", validThruDate=").append(validThruDate);
        sb.append(", cvc='").append(cvc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
