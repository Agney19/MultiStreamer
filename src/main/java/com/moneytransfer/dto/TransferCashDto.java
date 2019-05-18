package com.moneytransfer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public TransferCashDto(@JsonProperty("cash") BigDecimal cash,
                           @JsonProperty("toUserId") Long toUserId) {
        super(cash);
        this.toUserId = toUserId;
    }
}
