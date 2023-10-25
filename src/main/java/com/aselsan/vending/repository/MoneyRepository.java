package com.aselsan.vending.repository;

import com.aselsan.vending.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money, Integer> {
}
