package me.imatveev.thechat.storage;

import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.storage.ChatStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID>, ChatStorage {
    @Query("select chat from Chat chat" +
            " left join fetch User user" +
            " where user.id = :user_id")
    List<Chat> findByUserId(@Param("user_id") UUID userId);
}
