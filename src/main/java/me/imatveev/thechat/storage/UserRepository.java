package me.imatveev.thechat.storage;

import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.storage.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, UserStorage {
    @Override
    User save(User entity);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(UUID id);

    @Override
    Optional<User> findByPhone(String phone);

    @Query("select user from User user" +
            " left join fetch user.chats chat" +
            " where chat.id = :chat_id")
    List<User> findAllByChatId(@Param("chat_id") UUID chatId);
}
