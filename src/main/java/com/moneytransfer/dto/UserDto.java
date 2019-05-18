package com.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** Основная информация о пользователе */
@Getter
@ToString
@AllArgsConstructor
public final class UserDto {
    /** Персональные данные пользователя */
    private final PersonDto person;
    /** Email */
    private final String email;
}
