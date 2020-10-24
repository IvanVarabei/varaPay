package com.varabei.ivan.model.entity;

import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public enum Currency {
    BITCOIN(CurrencyConciseName.BTC.name(), "img/btc.png"),
    ETHEREUM(CurrencyConciseName.ETH.name(), "img/eth.png"),
    DASH(CurrencyConciseName.DASH.name(), "img/dash.png");

    private enum CurrencyConciseName {
        BTC, ETH, DASH
    }

    private static final Logger log = LogManager.getLogger(Currency.class);
    private static final int PAUSE_BETWEEN_COST_UPDATING = 60_000;
    private final String img;
    private BigDecimal cost;
    private final String conciseName;

    Currency(String conciseName, String img) {
        this.conciseName = conciseName;
        this.img = img;
        startCostUpdating();
    }

    public String getImg() {
        return img;
    }

    public BigDecimal getCost() {
        return cost;
    }

    void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getConciseName() {
        return conciseName;
    }

    private void startCostUpdating() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    this.setCost(DaoFactory.getInstance().getCurrencyDao().findCurrencyCostInUsd(this));
                    Thread.sleep(PAUSE_BETWEEN_COST_UPDATING);
                } catch (DaoException e) {
                    log.error(e);
                }catch (InterruptedException e){
                    log.error(e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
