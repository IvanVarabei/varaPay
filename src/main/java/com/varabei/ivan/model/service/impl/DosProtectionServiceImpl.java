package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.service.DosProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static final long ALLOWED_AMOUNT_OF_REQUESTS_PER_INTERVAL = 3000;
    private static final long INTERVAL_BETWEEN_CLEARING_HISTORY = 30_000;
    private Map<String, Integer> remoteAddressAndRequestAmount = new ConcurrentHashMap<>();
    private long historyClearingTime;

    @Override
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
