package com.epam.varapay.model.entity;

import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 * This enum contains cryptocurrencies and their characteristics such as
 * <ul>
 *     <li>Concise name</li>
 *     <li>Cost USD</li>
 *     <li>Image, sort of logo</li>
 *     <li>Wallet to accept payments</li>
 * </ul>
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.dao.impl.CurrencyDaoImpl
 * @see Bid
 */
public enum CustomCurrency {
    BITCOIN("BTC", "img/btc.png", "1F3BAUckj5dbwYSm9cPRmF73zCXjvjHyR6"),
    ETHEREUM("ETH", "img/eth.png", "0xc7fa6d4f3b985df30895f175a30d86b91ff17c96"),
    DASH("DASH", "img/dash.png", "Xywm9ps9wSKv562wkQVeChSCpxeofRPkDe");

    private static final Logger log = LogManager.getLogger(CustomCurrency.class);
    private static final int PAUSE_BETWEEN_COST_UPDATING = 60_000;
    private final String conciseName;
    private BigDecimal cost;
    private final String wallet;
    private final String img;

    /**
     * Initializes the CustomCurrency instance and calls method which starts daemon thread accessing the
     * {@link com.epam.varapay.model.dao.CurrencyDao}.
     *
     * @param conciseName the small number of letters representing full name.
     * @param img         the logo of the currency.
     * @param wallet      the wallet of particular currency, which is used to receive incoming payments from clients.
     */
    CustomCurrency(String conciseName, String img, String wallet) {
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

    /**
     * Starts daemon thread which will access {@link com.epam.varapay.model.dao.impl.CurrencyDaoImpl} each
     * {@link #PAUSE_BETWEEN_COST_UPDATING} to get actual currency cost. Received cost is being set to CustomCurrency
     * instance.
     */
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
