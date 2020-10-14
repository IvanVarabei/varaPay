package com.varabei.ivan.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment extends Identifiable {
    private Card sourceCard;
    private Card destinationCard;
    private BigDecimal amount;
    private LocalDateTime paymentInstant;

    public Card getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(Card sourceCard) {
        this.sourceCard = sourceCard;
    }

    public Card getDestinationCard() {
        return destinationCard;
    }

    public void setDestinationCard(Card destinationCard) {
        this.destinationCard = destinationCard;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentInstant() {
        return paymentInstant;
    }

    public void setPaymentInstant(LocalDateTime paymentInstant) {
        this.paymentInstant = paymentInstant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Payment payment = (Payment) o;

        if (sourceCard != null ? !sourceCard.equals(payment.sourceCard) : payment.sourceCard != null) return false;
        if (destinationCard != null ? !destinationCard.equals(payment.destinationCard) : payment.destinationCard != null)
            return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        return paymentInstant != null ? paymentInstant.equals(payment.paymentInstant) : payment.paymentInstant == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sourceCard != null ? sourceCard.hashCode() : 0);
        result = 31 * result + (destinationCard != null ? destinationCard.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (paymentInstant != null ? paymentInstant.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("sourceCard=").append(sourceCard);
        sb.append(", destinationCard=").append(destinationCard);
        sb.append(", amount=").append(amount);
        sb.append(", paymentInstant=").append(paymentInstant);
        sb.append('}');
        return sb.toString();
    }
}
