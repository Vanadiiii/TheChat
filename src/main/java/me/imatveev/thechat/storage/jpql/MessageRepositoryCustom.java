package me.imatveev.thechat.storage.jpql;

import me.imatveev.thechat.domain.entity.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MessageRepositoryCustom {
    List<Message> findAllByChatId(UUID chatId, Pageable pageable);
}
