package me.imatveev.thechat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.entity.ChatType;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.domain.service.UserService;
import me.imatveev.thechat.domain.storage.ChatStorage;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.exception.UnavailableChatFoundException;
import me.imatveev.thechat.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatStorage storage;
    private final UserService userService;

    @Override
    public Chat save(Chat chat) {
        log.info("try to save new chat - {}", chat);

        return storage.save(chat);
    }

    @Override
    public Optional<Chat> findById(UUID id) {
        log.info("try to find chat by id - {}", id);

        return storage.findById(id);
    }

    @Override
    public List<Chat> findByUserId(UUID userId) {
        log.info("try to find chats by user's id - {}", userId);

        return storage.findByUserId(userId);
    }

    @Override
    @Transactional
    public void joinToChat(UUID userId, UUID chatId) {
        log.info("try to join user ({}) to chat ({})", userId, chatId);

        final Chat chat = storage.findById(chatId)
                .orElseThrow(() -> ChatNotFoundException.of(chatId));

        if (chat.getType() == ChatType.DIRECT) {
            throw UnavailableChatFoundException.forDirect(userId, chatId);
        }

        final User user = userService.findById(userId)
                .orElseThrow(() -> UserNotFoundException.of(userId));

        chat.addUser(user);
    }
}
