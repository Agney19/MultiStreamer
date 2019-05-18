package com.moneytransfer.service;

import com.moneytransfer.dal.UserDao;
import com.moneytransfer.dto.CashDto;
import com.moneytransfer.dto.PersonDto;
import com.moneytransfer.dto.TransferCashDto;
import com.moneytransfer.dto.UserDto;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.Person;
import com.moneytransfer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/** Сервис по работе с пользователями */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /** Совершить перевод денег со счета пользователя на счет другого */
    @Transactional
    public void transferCash(long fromUserId, TransferCashDto dto) {
        BigDecimal cashChange = dto.getCash();
        Assert.notNull(cashChange, "Сумма перевода не задана");

        Assert.isTrue(cashChange.compareTo(BigDecimal.ZERO) >= 0,
                "Сумма перевода должна быть больше 0");

        updateCash(cashChange, dto.getToUserId());
        updateCash(cashChange.negate(), fromUserId);
    }

    /** Создать пользователя */
    @Transactional
    public void createUser(UserDto dto) {
        User user = new User();
        Person person = new Person();
        PersonDto personDto = dto.getPerson();
        fillPerson(person, personDto);
        user.setEmail(dto.getEmail());
        user.setAccount(new Account());
        userDao.save(user);
    }

    /** Получить счет пользователя */
    @Transactional(readOnly = true)
    public CashDto getCash(long userId) {
        User user = userDao.findById(userId);
        Assert.notNull(user, "Пользователь не найден");

        return new CashDto(user.getAccount().getCash());
    }

    /** Изменить счет пользователя */
    private void updateCash(BigDecimal cash, long userId) {
        User user = userDao.findById(userId);
        Assert.notNull(user, String.format("Пользователь с id %s не найден", userId));
        Account account = user.getAccount();
        account.changeAccount(cash, user);
    }

    private void fillPerson(Person person, PersonDto dto) {
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
    }
}
