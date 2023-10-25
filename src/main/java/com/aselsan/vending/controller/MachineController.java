package com.aselsan.vending.controller;

import com.aselsan.vending.entity.Product;
import com.aselsan.vending.request.BuyProduct;
import com.aselsan.vending.request.CollectMoney;
import com.aselsan.vending.response.BuyResponse;
import com.aselsan.vending.response.HearthBeat;
import com.aselsan.vending.service.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machine")
@AllArgsConstructor
public class MachineController {
    private MachineService machineService;
    @GetMapping("/hearth-beat")
    public HearthBeat hearthBeat(){
        return machineService.hearthBeat();
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        List<Product> list = machineService.getAllProducts();
        for(Product p: list){
            System.out.println(p);
        }

        return list;
    }

    @PostMapping("/buy")
    public ResponseEntity<BuyResponse> buyProduct(@RequestBody BuyProduct buyProduct){

        return machineService.buyProduct(buyProduct);
    }

}
