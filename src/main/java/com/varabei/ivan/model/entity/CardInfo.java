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
