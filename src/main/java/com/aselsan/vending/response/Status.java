package com.aselsan.vending.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Status {
    String message;
    int code;
}
