package com.aselsan.vending.service;

import com.aselsan.vending.algorithm.BuyExchangeCalculator;
import com.aselsan.vending.customexceptions.ExchangeCalculatorException;
import com.aselsan.vending.entity.Money;
import com.aselsan.vending.entity.Product;
import com.aselsan.vending.repository.MoneyRepository;
import com.aselsan.vending.repository.ProductRepository;
import com.aselsan.vending.request.BuyProduct;
import com.aselsan.vending.response.BuyResponse;
import com.aselsan.vending.response.HearthBeat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class MachineService {
    private HearthBeat hearthBeat;
    private ProductRepository productRepository;
    private MoneyRepository moneyRepository;

    public MachineService(ProductRepository productRepository, MoneyRepository moneyRepository) {
        this.productRepository = productRepository;
        this.moneyRepository = moneyRepository;
    }

    public HearthBeat hearthBeat() {
        return hearthBeat;
    }

    public List<Product> getAllProducts() {
        productRepository.findAll();
        return productRepository.findAll();
    }

    public ResponseEntity<BuyResponse> buyProduct(BuyProduct buyProduct) {
        try {
            Optional<Product> p = productRepository.findById(buyProduct.getProductId());
            if (p.isEmpty() || p.get().getStock() <= 0 || buyProduct.getMoneys() == null)
                throw new ExchangeCalculatorException("incorrect product or money");

            Product product = p.get();
            int total = 0;
            for (Integer money : buyProduct.getMoneys()) {
                if (money <= 0)
                    throw new ExchangeCalculatorException("fake money");
                total += money;
            }

            if (total < product.getPrice())
                throw new ExchangeCalculatorException("not enough balance");

            List<Money> moneys = moneyRepository.findAll();
            int exchanges[] = new BuyExchangeCalculator().calculateExchanges(moneys, buyProduct, product, total);
            if(exchanges == null)
                throw new ExchangeCalculatorException("machine does not have exchange");

            moneyRepository.saveAll(moneys);
            product.setStock(product.getStock() - 1);
            productRepository.save(product);
            return new ResponseEntity<>(new BuyResponse(true, "afiyet olsun", exchanges), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BuyResponse(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
