package com.aselsan.vending.service;

import com.aselsan.vending.entity.Money;
import com.aselsan.vending.entity.Product;
import com.aselsan.vending.repository.MoneyRepository;
import com.aselsan.vending.repository.ProductRepository;
import com.aselsan.vending.request.BuyProduct;
import com.aselsan.vending.response.BuyResponse;
import com.aselsan.vending.scheduler.CoolingScheduler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MachineServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MoneyRepository moneyRepository;
    private AutoCloseable autoCloseable;
    private MachineService machineService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        machineService = new MachineService(productRepository, moneyRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void hearthBeat() {
        CoolingScheduler coolingScheduler = new CoolingScheduler(machineService);
        coolingScheduler.run();

        assertNotNull(machineService.hearthBeat());
    }

    @Test
    void getAllProducts() {
        machineService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    void itShouldBuyProductSuccessfully() {
        Product product = new Product(1L,"name", "img.png", 15,1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(moneyRepository.findAll()).thenReturn(List.of());

        BuyProduct buyProduct = new BuyProduct(1,new int[]{5,5,5});
        ResponseEntity<BuyResponse> buyResponseResponseEntity = machineService.buyProduct(buyProduct);

        assertSame(buyResponseResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }


    @Test
    void itShouldReturnIncorrectProductId() {
        BuyProduct buyProduct = new BuyProduct(1,new int[]{5,5,5});
        ResponseEntity<BuyResponse> buyResponseResponseEntity = machineService.buyProduct(buyProduct);
        assertEquals(buyResponseResponseEntity.getBody().getMessage(), "incorrect product or money");
    }


    @Test
    void itShouldCatchFakeMoney() {
        Product product = new Product(1L,"name", "img.png", 15,1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(moneyRepository.findAll()).thenReturn(List.of());

        BuyProduct buyProduct = new BuyProduct(1,new int[]{-5,5,5});
        ResponseEntity<BuyResponse> buyResponseResponseEntity = machineService.buyProduct(buyProduct);

        assertEquals(buyResponseResponseEntity.getBody().getMessage(), "fake money");
    }



    @Test
    void itShouldCatchInsufficientBalance() {
        Product product = new Product(1L,"name", "img.png", 15,1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(moneyRepository.findAll()).thenReturn(List.of());

        BuyProduct buyProduct = new BuyProduct(1,new int[]{5});
        ResponseEntity<BuyResponse> buyResponseResponseEntity = machineService.buyProduct(buyProduct);

        assertEquals(buyResponseResponseEntity.getBody().getMessage(), "not enough balance");
    }

    @Test
    void itShouldCheckWhenMachineDoesNotHaveEnoughExchange() {
        Product product = new Product(1L,"name", "img.png", 15,1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(moneyRepository.findAll()).thenReturn(List.of(new Money(40,0)));

        BuyProduct buyProduct = new BuyProduct(1,new int[]{20, 20});
        ResponseEntity<BuyResponse> buyResponseResponseEntity = machineService.buyProduct(buyProduct);

        assertEquals(buyResponseResponseEntity.getBody().getMessage(), "machine does not have exchange");
    }
}