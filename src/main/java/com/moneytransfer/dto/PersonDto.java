package com.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** Информация о персональных данных пользователя */
@Getter
@ToString
@AllArgsConstructor
public final class PersonDto {
    /** Имя */
    private final String name;
    /** Фамилия */
    private final String surname;
}
