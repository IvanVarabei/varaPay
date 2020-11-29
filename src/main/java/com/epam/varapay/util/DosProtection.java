package com.epam.varapay.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosProtection {
    private static final DosProtection instance = new DosProtection();
    private static final long ALLOWED_AMOUNT_OF_REQUESTS_PER_INTERVAL = 100;
    private static final long INTERVAL_BETWEEN_CLEARING_HISTORY = 30_000;
    private Map<String, Integer> remoteAddressAndRequestAmount = new ConcurrentHashMap<>();
    private long historyClearingTime;

    private DosProtection() {
    }

    public static DosProtection getInstance() {
        return instance;
    }

    public boolean isAllowed(String remoteAddr) {
        clearHistoryIfTimeExpired();
        Integer count = remoteAddressAndRequestAmount.get(remoteAddr);
        if (count == null) {
            count = 1;
        } else {
            if (count > ALLOWED_AMOUNT_OF_REQUESTS_PER_INTERVAL) {
                return false;
            }
            count++;
        }
        remoteAddressAndRequestAmount.put(remoteAddr, count);
        return true;
    }

    private void clearHistoryIfTimeExpired() {
        if (historyClearingTime != 0 &&
                System.currentTimeMillis() - historyClearingTime > INTERVAL_BETWEEN_CLEARING_HISTORY) {
            remoteAddressAndRequestAmount.clear();
            historyClearingTime = 0;
        }
        if (historyClearingTime == 0) {
            historyClearingTime = System.currentTimeMillis();
        }
    }
}
