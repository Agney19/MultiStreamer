package com.moneytransfer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Аккаунт (счет пользователя) */
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account extends AbstractModel {
    /** Счет */
    @Column(name = "cash", nullable = false)
    private BigDecimal cash = BigDecimal.ZERO;

    /** История изменения счета */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", nullable = false)
    private List<AccountChangeInfo> changes = new ArrayList<>();

    public void changeAccount(BigDecimal cashChange, User initiator) {
        Assert.notNull(cashChange, "cashChange is null");
        Assert.notNull(initiator, "initiator is null");

        updateCash(cashChange);
        addHistory(cashChange, initiator);
    }

    private void updateCash(BigDecimal cashChange) {
        BigDecimal resultCash = cash.add(cashChange);
        if (resultCash.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("На счету недостаточно средств");
        }
        cash = resultCash;
    }

    private void addHistory(BigDecimal cashChange, User initiator) {
        AccountChangeInfo info = new AccountChangeInfo();
        info.setChange(cashChange);
        info.setInitiator(initiator);
        changes.add(info);
    }
}
