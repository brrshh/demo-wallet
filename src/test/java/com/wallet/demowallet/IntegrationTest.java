package com.wallet.demowallet;

import com.wallet.demowallet.dto.ChangeAmount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {

    private TestRestTemplate restTemplate;


    private ConfigurableApplicationContext app;

    @BeforeEach
    public void init() {
        this.app = SpringApplication.run(DemoWalletApplication.class);
        restTemplate = new TestRestTemplate();
    }

    @AfterEach
    public void close() {
        this.app.close();
    }

    //Create a wallet with amoun 500, execute add and substract amounts and check finish amount.
    @Test
    void testParallelExecution() {
        Integer walletId = 1;
        ResponseEntity<Void> response = addAmount(walletId, new BigDecimal(500));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<String> amount = restTemplate.getForEntity("http://localhost:8080/wallet/1", String.class);
        assertEquals(HttpStatus.OK, amount.getStatusCode());
        System.out.println(amount.getBody());
        assertTrue(amount.getBody().contains("500"));


        Stream<Supplier<ResponseEntity<Void>>> addAmounts = IntStream.range(0, 50)
                .mapToObj(i -> () -> addAmount(walletId, new BigDecimal(10)));

        Stream<Supplier<ResponseEntity<Void>>> substractsAmounts = IntStream.range(0, 50)
                .mapToObj(i -> () -> substractAmount(walletId, new BigDecimal(10)));

        List<Supplier<ResponseEntity<Void>>> actions = Stream.concat(addAmounts, substractsAmounts)
                .collect(Collectors.toList());

        Collections.shuffle(actions);

        boolean resultAction = actions.stream()
                .parallel()
                .map(Supplier::get)
                .allMatch(resp -> resp.getStatusCode().is2xxSuccessful());

        assertTrue(resultAction);

        amount = restTemplate.getForEntity("http://localhost:8080/wallet/1", String.class);
        assertEquals(HttpStatus.OK, amount.getStatusCode());
        assertTrue(amount.getBody().contains("500.00"), "Finish amount =" + amount.getBody());
    }

    public ResponseEntity<Void> addAmount(Integer walletId, BigDecimal amount) {
        return restTemplate.postForEntity("http://localhost:8080/wallet", new ChangeAmount(walletId, amount), Void.class);
    }

    public ResponseEntity<Void> substractAmount(Integer walletId, BigDecimal amount) {
        return restTemplate.exchange("http://localhost:8080/wallet", HttpMethod.PUT, new HttpEntity<>(new ChangeAmount(walletId, amount)), Void.class);
    }
}
