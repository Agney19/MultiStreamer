package com.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/** Информация о переводе денег со счета */
@Getter
@ToString(callSuper = true)
public final class TransferCashDto extends CashDto {
    /** Идентификатор пользователя, которому совершается перевод денег */
    private final Long toUserId;

    public TransferCashDto(BigDecimal cash, long toUserId) {
        super(cash);
        this.toUserId = toUserId;
    }
}
