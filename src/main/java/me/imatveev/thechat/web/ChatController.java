package me.imatveev.thechat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.web.mapper.ChatMapper;
import me.imatveev.thechat.web.model.ChatDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/join")
    public void joinToChat(@RequestParam UUID userId, @RequestParam UUID chatId) {
        service.joinToChat(userId, chatId);
    }

    @PostMapping
    public Chat save(@RequestBody ChatDto chat) {
        //todo
        throw new RuntimeException("not implemented yet");
    }
}
