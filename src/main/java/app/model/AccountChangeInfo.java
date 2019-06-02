package app.model;

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

    /** Пользователь, кто переводит деньги */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User fromUser;

    /** Пользователь, кому переводятся деньги */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User toUser;
}
