package com.aselsan.vending.service;

import com.aselsan.vending.entity.Money;
import com.aselsan.vending.entity.Product;
import com.aselsan.vending.repository.MoneyRepository;
import com.aselsan.vending.repository.ProductRepository;
import com.aselsan.vending.request.CollectMoney;
import com.aselsan.vending.request.Login;
import com.aselsan.vending.request.UpdateProduct;
import com.aselsan.vending.response.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Value("${admin.password}")
    private String password;

    private ProductRepository productRepository;
    private MoneyRepository moneyRepository;

    public AdminService(ProductRepository productRepository, MoneyRepository moneyRepository){
        this.productRepository = productRepository;
        this.moneyRepository = moneyRepository;
    }

    public String reset() {
        productRepository.deleteAll();
        Product water = new Product(null, "water", "water.png", 25, 10);
        Product coke = new Product(null, "coke", "coke.png", 35, 10);
        Product soda = new Product(null, "soda", "soda.png", 45, 10);
        productRepository.saveAll(Arrays.asList(water, coke, soda));

        moneyRepository.deleteAll();
        Money m1 = new Money(1, 3);
        Money m5 = new Money(5, 3);
        Money m10 = new Money(10, 3);
        Money m20 = new Money(20, 3);
        moneyRepository.saveAll(Arrays.asList(m1, m5, m10, m20));

        return "ok";
    }

    public ResponseEntity<Status> updateProduct(long id, UpdateProduct updateProduct) {
        if (updateProduct.getPrice() < 0 || updateProduct.getStock() < 0)
            return new ResponseEntity<>(new Status("price and stock must be greater than zero", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        Product product = productRepository.findById(id).orElseThrow();
        product.setPrice(updateProduct.getPrice());
        product.setStock(updateProduct.getStock());

        productRepository.save(product);

        return new ResponseEntity<>(new Status("ok", HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED);
    }

    public List<Money> getMoneys() {
        return moneyRepository.findAll();
    }

    public ResponseEntity<Status> collectMoney(int unit, CollectMoney collectMoney) {
        Optional<Money> money = moneyRepository.findById(unit);
        System.out.println(collectMoney.getCount() <= 0);
        if(money.isEmpty() || collectMoney.getCount() <= 0)
            return new ResponseEntity<>(new Status("wrong unit id or count", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        Money m = money.get();
        if(m.getCount() < collectMoney.getCount())
            return new ResponseEntity<>(new Status("not enough money", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        m.setCount(m.getCount() - collectMoney.getCount());

        moneyRepository.save(m);

        return new ResponseEntity<>(new Status("ok", HttpStatus.ACCEPTED.value()), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Status> login(HttpServletRequest request, Login login){
        if(login.getUsername().equals("admin") && login.getPassword().equals(password)){
            request.getSession().setAttribute("user", "admin");
            return new ResponseEntity<>(new Status("login", 1), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new Status("invalid credentials", 0), HttpStatus.BAD_REQUEST);
    }
}
