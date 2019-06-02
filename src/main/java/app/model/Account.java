package app.model;

import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Аккаунт (счет пользователя) */
@Getter
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

    public void changeAccount(BigDecimal cashChange, User fromUser, User toUser) {
        Assert.notNull(cashChange, "cashChange is null");
        Assert.notNull(fromUser, "fromUser is null");
        Assert.notNull(toUser, "toUser is null");

        updateCash(cashChange);
        addHistory(cashChange, fromUser, toUser);
    }

    private void updateCash(BigDecimal cashChange) {
        BigDecimal resultCash = cash.add(cashChange);
        Assert.isTrue(resultCash.compareTo(BigDecimal.ZERO) >= 0, "На счету недостаточно средств");
        cash = resultCash;
    }

    private void addHistory(BigDecimal cashChange, User fromUser, User toUser) {
        AccountChangeInfo info = new AccountChangeInfo();
        info.setChange(cashChange);
        info.setFromUser(fromUser);
        info.setToUser(toUser);
        changes.add(info);
    }
}
