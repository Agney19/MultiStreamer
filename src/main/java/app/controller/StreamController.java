package app.controller;

import app.dto.UserDto;
import app.service.StreamService;
import app.dto.CashDto;
import app.dto.TransferCashDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class StreamController {

    @Autowired
    private StreamService streamService;

    /** Изменить счет пользователя */
    @PostMapping("broadcast")
    public void transferCash(@RequestBody TransferCashDto dto) {
        streamService.transferCash(fromUserId, dto);
    }

    /** Получить счет пользователя */
    @GetMapping(value = "{userId}/cash", produces = MediaType.APPLICATION_JSON_VALUE)
    public CashDto getCash(@PathVariable("userId") long userId) {
        return streamService.getCash(userId);
    }

    /** Создать пользователя */
    @PostMapping
    public void createUser(@RequestBody UserDto dto) {
        streamService.createUser(dto);
    }
}
