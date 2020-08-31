package com.wallet.demowallet.controller;

import com.wallet.demowallet.dto.ChangeAmount;
import com.wallet.demowallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
@Validated
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public void addAmount(@Valid @RequestBody ChangeAmount changeAmount) {
        walletService.addAmount(changeAmount.getWalletId(), changeAmount.getAmount());
    }

    @PutMapping
    public void subtractAmount(@Valid @RequestBody ChangeAmount changeAmount) {
        walletService.subtractAmount(changeAmount.getWalletId(), changeAmount.getAmount());
    }

    @GetMapping(path = "/{walletId}")
    public Map<String, String> getAmount(@PathVariable("walletId") Integer walletId) {
        return Map.of("amount", walletService.getAmount(walletId));
    }
}