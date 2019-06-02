package app.model;

import app.constants.CommonFieldLengths;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/** Пользователь */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends AbstractModel {
    /** Персональные данные */
    @Embedded
    private Person person;

    /** Email-адрес */
    @Column(name = "email", nullable = false, length = CommonFieldLengths.EMAIL)
    private String email;

    /** Счет */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, optional = false)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;
}
