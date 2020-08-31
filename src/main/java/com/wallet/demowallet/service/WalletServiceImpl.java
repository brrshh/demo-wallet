package com.wallet.demowallet.service;

import com.wallet.demowallet.domain.Wallet;
import com.wallet.demowallet.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class WalletServiceImpl implements WalletService {
    private static final String ZERO = "0";

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void setIsolation() {
        transactionTemplate.execute(status -> {
            walletRepository.setIsolation();
            return null;
        });
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    public void addAmount(Integer walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByIdLock(walletId)
                .orElse(new Wallet(null, BigDecimal.ZERO));

        wallet.setAmount(Optional.ofNullable(wallet.getAmount()).orElse(BigDecimal.ZERO).add(amount));
        walletRepository.save(wallet);
    }


    @Override
    public void subtractAmount(Integer walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByIdLock(walletId)
                .orElseThrow(() -> new EntityNotFoundException("WalletId = " + walletId));

        BigDecimal walletAmount = Optional.ofNullable(wallet.getAmount()).orElse(BigDecimal.ZERO);

        if (amount.compareTo(walletAmount) <= 0) {
            wallet.setAmount(wallet.getAmount().subtract(amount));
            walletRepository.save(wallet);
        } else {
            throw new UnsupportedOperationException("Wallet amount should be greater or equals of " + amount + ", but found wallet with amount is " + walletAmount);
        }
    }

    @Override
    public String getAmount(Integer walletId) {
        return Optional.ofNullable(getWalletById(walletId).getAmount())
                .map(BigDecimal::toString)
                .orElse(ZERO);
    }

    private Wallet getWalletById(Integer walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("WalletId = " + walletId));
    }
}
