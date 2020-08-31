package com.wallet.demowallet.service;

import com.wallet.demowallet.domain.Wallet;
import com.wallet.demowallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void addAmountOk() {
        Mockito.when(walletRepository.findByIdLock(1)).thenReturn(Optional.of(new Wallet(1, BigDecimal.ZERO)));
        walletService.addAmount(1, BigDecimal.ONE);
        Mockito.verify(walletRepository, Mockito.atLeastOnce()).save(new Wallet(1, BigDecimal.ONE));
    }

    @Test
    void subtractAmountOk() {
        Mockito.when(walletRepository.findByIdLock(1)).thenReturn(Optional.of(new Wallet(1, new BigDecimal(33))));
        walletService.subtractAmount(1, BigDecimal.ONE);
        Mockito.verify(walletRepository, Mockito.atLeastOnce()).save(new Wallet(1, new BigDecimal(32)));
    }

    @Test
    void getAmount() {
        Mockito.when(walletRepository.findById(1)).thenReturn(Optional.of(new Wallet(1, new BigDecimal(33))));
        walletService.getAmount(1);
        Mockito.verifyNoMoreInteractions(walletRepository);
    }
}