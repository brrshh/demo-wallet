package com.wallet.demowallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAmount {

    @NotNull
    private Integer walletId;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal amount;
}