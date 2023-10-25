package com.aselsan.vending.controller;

import com.aselsan.vending.entity.Money;
import com.aselsan.vending.request.CollectMoney;
import com.aselsan.vending.request.Login;
import com.aselsan.vending.request.UpdateProduct;
import com.aselsan.vending.response.Status;
import com.aselsan.vending.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService service;

    @PostMapping("/reset")
    public String hello(){
        return service.reset();
    }

    @GetMapping("/moneys")
    public List<Money> getMoneys(){
        return service.getMoneys();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Status> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProduct product){
        return service.updateProduct(id, product);
    }

    @PutMapping("/money/{unit}/collect")
    public ResponseEntity<Status> collectMoney(@PathVariable("unit") int unit, @RequestBody CollectMoney collectMoney){
        System.out.println(unit);
        System.out.println(collectMoney.getCount());
        return service.collectMoney(unit, collectMoney);
    }

    @PostMapping("/login")
    public ResponseEntity<Status> login(HttpServletRequest request, @RequestBody Login login){
        return service.login(request, login);
    }


    @GetMapping("/status")
    public Status status(){
        return new Status("login", 1);
    }

}
