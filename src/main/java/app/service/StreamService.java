package app.service;

import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Account;
import app.model.Person;
import app.dal.UserDao;
import app.dto.CashDto;
import app.dto.TransferCashDto;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

/** Сервис по работе с пользователями */
@Service
public class StreamService {
    @Autowired
    private UserDao userDao;

    /** Совершить перевод денег со счета одного пользователя на счет другого */
    @Transactional
    public void transferCash(long fromUserId, TransferCashDto dto) {
        BigDecimal cashChange = dto.getCash();
        Assert.notNull(cashChange, "Сумма перевода не задана");

        Assert.isTrue(cashChange.compareTo(BigDecimal.ZERO) >= 0,
                "Сумма перевода должна быть больше 0");

        User fromUser = Optional.ofNullable(userDao.findById(fromUserId))
                .orElseThrow(() -> new IllegalArgumentException("Инициатор перевода не найден"));
        User toUser = Optional.ofNullable(userDao.findById(dto.getToUserId()))
                .orElseThrow(() -> new IllegalArgumentException("Адресат перевода не найден"));
        updateCash(cashChange, toUser, fromUser, toUser);
        updateCash(cashChange.negate(), fromUser, fromUser, toUser);
    }

    /** Создать пользователя */
    @Transactional
    public void createUser(UserDto dto) {
        User user = new User();
        Person person = new Person();
        PersonDto personDto = dto.getPerson();
        fillPerson(person, personDto);
		user.setPerson(person);
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
    private void updateCash(BigDecimal cash, User accountHost, User fromUser, User toUser) {
        Assert.isTrue(accountHost == fromUser || accountHost == toUser, "Неверный владелец счета");

        Account account = accountHost.getAccount();
        account.changeAccount(cash, fromUser, toUser);
    }

    private void fillPerson(Person person, PersonDto dto) {
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
    }
}
