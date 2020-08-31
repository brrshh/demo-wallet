package com.wallet.demowallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.demowallet.dto.ChangeAmount;
import com.wallet.demowallet.service.WalletService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addAmount() throws Exception {
        mockMvc.perform(post("/wallet/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new ChangeAmount(1, BigDecimal.ONE)))
        ).andExpect(status().isOk());
        Mockito.verify(walletService, Mockito.atLeastOnce()).addAmount(1, BigDecimal.ONE);
    }

    @Test
    void subtractAmount() throws Exception {
        mockMvc.perform(put("/wallet/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new ChangeAmount(1, BigDecimal.ONE)))
        ).andExpect(status().isOk());
        Mockito.verify(walletService, Mockito.atLeastOnce()).subtractAmount(1, BigDecimal.ONE);

    }

    @Test
    void getAmount() throws Exception {
        Mockito.when(walletService.getAmount(3)).thenReturn("33");
        mockMvc.perform(get("/wallet/3/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount", Is.is("33")));
    }
}