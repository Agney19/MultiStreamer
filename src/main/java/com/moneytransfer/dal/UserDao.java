package com.moneytransfer.dal;

import com.moneytransfer.model.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.repository.Repository;

public interface UserDao extends Repository<User, Long> {

    @Nullable
    User findById(long id);

    void save(User user);
}
