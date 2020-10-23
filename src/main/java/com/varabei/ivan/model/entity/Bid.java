package com.varabei.ivan.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid extends Identifiable {
    private Account account;
    private BidState state;
    private BigDecimal amount;
    private String clientMessage;
    private String adminComment;
    private LocalDateTime placingDateTime;
    private boolean isTopUp;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BidState getState() {
        return state;
    }

    public void setState(BidState state) {
        this.state = state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public LocalDateTime getPlacingDateTime() {
        return placingDateTime;
    }

    public void setPlacingDateTime(LocalDateTime placingDateTime) {
        this.placingDateTime = placingDateTime;
    }

    public boolean isTopUp() {
        return isTopUp;
    }

    public void setTopUp(boolean topUp) {
        isTopUp = topUp;
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
        Bid bid = (Bid) o;
        if (isTopUp != bid.isTopUp) {
            return false;
        }
        if (account != null ? !account.equals(bid.account) : bid.account != null) {
            return false;
        }
        if (state != null ? !state.equals(bid.state) : bid.state != null) {
            return false;
        }
        if (amount != null ? !amount.equals(bid.amount) : bid.amount != null) {
            return false;
        }
        if (clientMessage != null ? !clientMessage.equals(bid.clientMessage) : bid.clientMessage != null) {
            return false;
        }
        if (adminComment != null ? !adminComment.equals(bid.adminComment) : bid.adminComment != null) {
            return false;
        }
        return placingDateTime != null ? placingDateTime.equals(bid.placingDateTime) : bid.placingDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (clientMessage != null ? clientMessage.hashCode() : 0);
        result = 31 * result + (adminComment != null ? adminComment.hashCode() : 0);
        result = 31 * result + (placingDateTime != null ? placingDateTime.hashCode() : 0);
        result = 31 * result + (isTopUp ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bid{");
        sb.append("account=").append(account);
        sb.append(", state='").append(state).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", clientMessage='").append(clientMessage).append('\'');
        sb.append(", adminComment='").append(adminComment).append('\'');
        sb.append(", placingDateTime=").append(placingDateTime);
        sb.append(", isTopUp=").append(isTopUp);
        sb.append('}');
        return sb.toString();
    }
}
