package com.moneytransfer.controller;

import com.moneytransfer.dto.CashDto;
import com.moneytransfer.dto.TransferCashDto;
import com.moneytransfer.dto.UserDto;
import com.moneytransfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/** Контроллер для работы с пользователями */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /** Изменить счет пользователя */
    @PostMapping("{userId}/cash")
    public void transferCash(@PathVariable("userId") long userId,
                             @RequestBody TransferCashDto dto) {
        userService.transferCash(userId, dto);
    }

    /** Получить счет пользователя */
    @GetMapping("{userId}/cash")
    public CashDto getCash(@PathVariable("userId") long userId) {
        return userService.getCash(userId);
    }

    /** Создать пользователя */
    @PostMapping
    public void createUser(@RequestBody UserDto dto) {
        userService.createUser(dto);
    }
}
