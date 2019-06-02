package app.model;

import app.constants.CommonFieldLengths;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/** Персональные данные пользователя */
@Getter
@Setter
@Embeddable
public class Person {
    /** Имя */
    @Column(name = "name", nullable = false, length = CommonFieldLengths.NAME)
    private String name;

    /** Фамилие */
    @Column(name = "surname", nullable = false, length = CommonFieldLengths.SURNAME)
    private String surname;
}
