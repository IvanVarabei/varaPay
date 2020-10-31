package com.varabei.ivan.model.entity;

import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public enum Currency {
    BITCOIN("BTC", "img/btc.png", "1F3BAUckj5dbwYSm9cPRmF73zCXjvjHyR6"),
    ETHEREUM("ETH", "img/eth.png", "0xc7fa6d4f3b985df30895f175a30d86b91ff17c96"),
    DASH("DASH", "img/dash.png", "Xywm9ps9wSKv562wkQVeChSCpxeofRPkDe");

    private static final Logger log = LogManager.getLogger(Currency.class);
    private static final int PAUSE_BETWEEN_COST_UPDATING = 60_000;
    private final String conciseName;
    private BigDecimal cost;
    private final String wallet;
    private final String img;

    Currency(String conciseName, String img, String wallet) {
        this.conciseName = conciseName;
        this.img = img;
        this.wallet = wallet;
        startCostUpdating();
    }

    public String getConciseName() {
        return conciseName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getImg() {
        return img;
    }

    public String getWallet() {
        return wallet;
    }

    private void startCostUpdating() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    this.setCost(DaoFactory.getInstance().getCurrencyDao().findCurrencyCostInUsd(this));
                } catch (DaoException e) {
                    log.error(e);
                }
                try {
                    Thread.sleep(PAUSE_BETWEEN_COST_UPDATING);
                } catch (InterruptedException e) {
                    log.error(e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
