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

    @Query("select user from User user" +
            " left join fetch Chat chat" +
            " where chat.id = :chat_id")
    List<User> findAllByChatId(@Param("chat_id") UUID chatId);
}
