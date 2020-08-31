package com.wallet.demowallet.service;

import java.math.BigDecimal;

public interface WalletService {

    void addAmount(Integer walletId, BigDecimal amount);

    void subtractAmount(Integer walletId, BigDecimal amount);

    String getAmount(Integer walletId);
}
