package me.imatveev.thechat.domain.service;

import me.imatveev.thechat.domain.entity.Message;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<Message> findInChat(UUID chatId, int page, int size);

    Path exportAll(UUID chatId);

    void send(UUID userId, UUID chatId, String text);
}
