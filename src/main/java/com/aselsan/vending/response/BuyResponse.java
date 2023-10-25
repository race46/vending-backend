package com.aselsan.vending.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyResponse {
    private boolean success;
    private String message;
    private int[] exchanges;
}
