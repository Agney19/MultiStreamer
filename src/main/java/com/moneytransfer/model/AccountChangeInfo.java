package com.moneytransfer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** История изменений счета пользователя */
@Getter
@Setter
@Entity
@Table(name = "account_change_info")
public class AccountChangeInfo extends AbstractModel {
    /** Изменение счета */
    @Column(name = "change", nullable = false)
    private BigDecimal change;

    /** Время изменения счета */
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    /** Инициатор изменения счета */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User initiator;
}
