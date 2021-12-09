package me.imatveev.thechat.domain.service;

import me.imatveev.thechat.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    List<User> findByChatId(UUID chatId);
}
