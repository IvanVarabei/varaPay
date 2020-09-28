package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment implements Serializable {
    private Long paymentId;
    private Card sourceCard;
    private Card destinationCard;
    private BigDecimal amount;
    private LocalDate paymentInstant;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

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

    public LocalDate getPaymentInstant() {
        return paymentInstant;
    }

    public void setPaymentInstant(LocalDate paymentInstant) {
        this.paymentInstant = paymentInstant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paymentId != null ? !paymentId.equals(payment.paymentId) : payment.paymentId != null) return false;
        if (sourceCard != null ? !sourceCard.equals(payment.sourceCard) : payment.sourceCard != null) return false;
        if (destinationCard != null ? !destinationCard.equals(payment.destinationCard) : payment.destinationCard != null)
            return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        return paymentInstant != null ? paymentInstant.equals(payment.paymentInstant) : payment.paymentInstant == null;
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + (sourceCard != null ? sourceCard.hashCode() : 0);
        result = 31 * result + (destinationCard != null ? destinationCard.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (paymentInstant != null ? paymentInstant.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("paymentId=").append(paymentId);
        sb.append(", sourceCard=").append(sourceCard);
        sb.append(", destinationCard=").append(destinationCard);
        sb.append(", amount=").append(amount);
        sb.append(", paymentInstant=").append(paymentInstant);
        sb.append('}');
        return sb.toString();
    }
}
