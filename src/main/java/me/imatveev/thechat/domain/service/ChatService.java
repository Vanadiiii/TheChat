package me.imatveev.thechat.domain.service;

import me.imatveev.thechat.domain.entity.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatService {
    Chat save(Chat chat);

    Optional<Chat> findById(UUID id);

    List<Chat> findByUserId(UUID userId);

    void joinToChat(UUID userId, UUID chatId);
}
