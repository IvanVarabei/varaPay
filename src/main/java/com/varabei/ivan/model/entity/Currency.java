package com.varabei.ivan.model.entity;

public enum Currency {
    BITCOIN(CurrencyConciseName.BTC.name()),
    ETHEREUM(CurrencyConciseName.ETH.name());

    private enum CurrencyConciseName {
        BTC, ETH
    }

    private final String conciseName;

    Currency(String conciseName) {
        this.conciseName = conciseName;
    }

    public String getConciseName() {
        return conciseName;
    }
}
