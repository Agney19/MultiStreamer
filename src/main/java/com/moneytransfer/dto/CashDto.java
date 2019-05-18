package com.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/** Информация об счете */
@Getter
@ToString
@AllArgsConstructor
public class CashDto {
    /** Счет */
    private final BigDecimal cash;
}
