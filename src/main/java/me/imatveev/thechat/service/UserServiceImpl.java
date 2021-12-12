package me.imatveev.thechat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.UserService;
import me.imatveev.thechat.domain.storage.UserStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage storage;

    @Override
    @Transactional
    public User save(User user) {
        log.info("try to save new user - {}", user);

        return storage.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        log.info("try to find user by id - {}", id);

        return storage.findById(id);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        log.info("try to find user by phone - {}", phone);

        return storage.findByPhone(phone);
    }

    @Override
    public List<User> findAll() {
        log.info("try to find all users");

        return storage.findAll();
    }

    @Override
    public List<User> findByChatId(UUID chatId) {
        log.info("try to find all users in chat with id - {}", chatId);

        return storage.findAllByChatId(chatId);
    }
}
