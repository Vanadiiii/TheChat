package me.imatveev.thechat.domain.storage;

import me.imatveev.thechat.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStorage {
    User save(User entity);

    List<User> findAll();

    Optional<User> findById(UUID id);

    Optional<User> findByPhone(String phone);

    List<User> findAllByChatId(UUID chatId);
}
