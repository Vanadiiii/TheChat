package me.imatveev.thechat.domain.storage;

import me.imatveev.thechat.domain.entity.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MessageStorage {
    List<Message> findAllByChatId(UUID chatId, Pageable pageable);

    Message save(Message message);
}
