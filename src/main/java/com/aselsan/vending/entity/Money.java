package com.aselsan.vending.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moneys")
@Data @AllArgsConstructor
@NoArgsConstructor
public class Money {
    @Id
    Integer unit;
    Integer count;
}
