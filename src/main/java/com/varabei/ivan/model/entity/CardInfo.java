package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class CardInfo extends StorableItem implements Serializable {
    private String cardNumber;
    private LocalDate validThruDate;
    private String cvc;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CardInfo cardInfo = (CardInfo) o;
        if (cardNumber != null ? !cardNumber.equals(cardInfo.cardNumber) : cardInfo.cardNumber != null) {
            return false;
        }
        if (validThruDate != null ? !validThruDate.equals(cardInfo.validThruDate) : cardInfo.validThruDate != null) {
            return false;
        }
        return cvc != null ? cvc.equals(cardInfo.cvc) : cardInfo.cvc == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (validThruDate != null ? validThruDate.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardInfo{");
        sb.append(", cardNumber='").append(cardNumber).append('\'');
        sb.append(", validThruDate=").append(validThruDate);
        sb.append(", cvc='").append(cvc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
