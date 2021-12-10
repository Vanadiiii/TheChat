package me.imatveev.thechat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.web.mapper.ChatMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/the-chat/v1/chats")
public class ChatController {
    private final ChatService service;
    private final ChatMapper mapper;

    @GetMapping("/{id}")
    public Chat findById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> ChatNotFoundException.of(id));
    }

    @GetMapping("/users/{userId}")
    public List<Chat> findByUserId(@PathVariable UUID userId) {
        return service.findByUserId(userId);
    }

    @PutMapping("/start-direct-chat")
    public Chat startDirectChat(@RequestParam UUID user1Id, @RequestParam UUID user2Id) {
        return service.startDirectChat(user1Id, user2Id);
    }

    @PutMapping("/start-group-chat")
    public Chat startDirectChat(@RequestParam UUID userId,
                                @RequestParam List<UUID> userIds,
                                @RequestParam String chatName) {
        return service.startGroupChat(userId, userIds, chatName);
    }

    @PutMapping("/join")
    public void joinToChat(@RequestParam UUID userId, @RequestParam UUID chatId) {
        service.joinToChat(userId, chatId);
    }
}
