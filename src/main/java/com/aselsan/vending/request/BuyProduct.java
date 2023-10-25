package com.aselsan.vending.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class BuyProduct {
    private long productId;
    private int[] moneys;
}
