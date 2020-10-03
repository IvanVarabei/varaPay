package com.varabei.ivan.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment implements Serializable {
    private Long paymentId;
    private CardInfo sourceCardInfo;
    private CardInfo destinationCardInfo;
    private BigDecimal amount;
    private LocalDateTime paymentInstant;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public CardInfo getSourceCardInfo() {
        return sourceCardInfo;
    }

    public void setSourceCardInfo(CardInfo sourceCardInfo) {
        this.sourceCardInfo = sourceCardInfo;
    }

    public CardInfo getDestinationCardInfo() {
        return destinationCardInfo;
    }

    public void setDestinationCardInfo(CardInfo destinationCardInfo) {
        this.destinationCardInfo = destinationCardInfo;
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

        Payment payment = (Payment) o;

        if (paymentId != null ? !paymentId.equals(payment.paymentId) : payment.paymentId != null) return false;
        if (sourceCardInfo != null ? !sourceCardInfo.equals(payment.sourceCardInfo) : payment.sourceCardInfo != null)
            return false;
        if (destinationCardInfo != null ? !destinationCardInfo.equals(payment.destinationCardInfo) : payment.destinationCardInfo != null)
            return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        return paymentInstant != null ? paymentInstant.equals(payment.paymentInstant) : payment.paymentInstant == null;
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + (sourceCardInfo != null ? sourceCardInfo.hashCode() : 0);
        result = 31 * result + (destinationCardInfo != null ? destinationCardInfo.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (paymentInstant != null ? paymentInstant.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("paymentId=").append(paymentId);
        sb.append(", sourceCardInfo=").append(sourceCardInfo);
        sb.append(", destinationCardInfo=").append(destinationCardInfo);
        sb.append(", amount=").append(amount);
        sb.append(", paymentInstant=").append(paymentInstant);
        sb.append('}');
        return sb.toString();
    }
}
