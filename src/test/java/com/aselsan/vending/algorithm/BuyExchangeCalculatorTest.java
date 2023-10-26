package com.aselsan.vending.algorithm;

import com.aselsan.vending.entity.Money;
import com.aselsan.vending.entity.Product;
import com.aselsan.vending.request.BuyProduct;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyExchangeCalculatorTest {

    @Test
    void itShouldCheckIfReturnsNullWhenMachineDoesNotHaveEnoughExchange() {
        BuyExchangeCalculator calculator = new BuyExchangeCalculator();
        List<Money> moneys = Arrays.asList(
                new Money(1,0),
                new Money(5, 0),
                new Money(10,0),
                new Money(20, 0));

        int moneyArr[] = {20, 20};
        BuyProduct buyProduct = new BuyProduct(1, moneyArr);

        Product product = new Product(1L, "name", "img.png", 35,1);

        int response[] = calculator.calculateExchanges(moneys, buyProduct, product, 40);

        assertEquals(response, null);
    }

    @Test
    void itShouldCheckIfReturnsIsEmptyArrayWhenTotalEqualsToProductPrice() {
        BuyExchangeCalculator calculator = new BuyExchangeCalculator();
        List<Money> moneys = Arrays.asList(
                new Money(1,0),
                new Money(5, 0),
                new Money(10,0),
                new Money(20, 0));

        int moneyArr[] = {5, 10, 20};
        BuyProduct buyProduct = new BuyProduct(1, moneyArr);

        Product product = new Product(1L, "name", "img.png", 35,1);

        int response[] = calculator.calculateExchanges(moneys, buyProduct, product, 35);

        assertArrayEquals(response, new int[0]);
    }

    @Test
    void itShouldCalculateExchangeProperly() {
        BuyExchangeCalculator calculator = new BuyExchangeCalculator();
        List<Money> moneys = Arrays.asList(
                new Money(1,5),
                new Money(5, 0),
                new Money(10,0),
                new Money(20, 0));

        int moneyArr[] = {20, 20};
        BuyProduct buyProduct = new BuyProduct(1, moneyArr);

        Product product = new Product(1L, "name", "img.png", 35,1);

        int response[] = calculator.calculateExchanges(moneys, buyProduct, product, 40);

        assertArrayEquals(response, new int[]{1,1,1,1,1});
    }
}