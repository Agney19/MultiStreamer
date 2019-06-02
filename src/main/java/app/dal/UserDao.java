package app.dal;

import app.model.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserDao extends Repository<User, Long> {

    @Nullable
    User findById(long id);

    List<User> findAll();

    void save(User user);
}
