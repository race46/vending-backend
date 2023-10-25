package com.aselsan.vending.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class HearthBeat {
    String status;
    boolean isCooling;
    double temperature;
}
