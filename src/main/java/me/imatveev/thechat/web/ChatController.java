package me.imatveev.thechat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.web.model.StartDirectChatDto;
import me.imatveev.thechat.web.model.StartGroupChatDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTERED')")
@RequestMapping("/the-chat/v1/chats")
public class ChatController {
    private final ChatService service;

    @GetMapping("/{id}")
    public Chat findById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> ChatNotFoundException.of(id));
    }

    @GetMapping
    public List<Chat> findAllUsersChats(@AuthenticationPrincipal User principal) {
        return service.findByUserId(principal.getId());
    }

    @PostMapping("/direct-chat")
    public Chat startDirectChat(@AuthenticationPrincipal User principal,
                                @RequestBody StartDirectChatDto chatDto) {
        return service.startDirectChat(
                principal.getId(),
                chatDto.getCompanionId()
        );
    }

    @PostMapping("/group-chat")
    public Chat startDirectChat(@AuthenticationPrincipal User principal,
                                @RequestBody StartGroupChatDto chatDto) {
        return service.startGroupChat(
                principal.getId(),
                chatDto.getCompanionIds(),
                chatDto.getChatName()
        );
    }

    @PutMapping("/join")
    public void joinToChat(@AuthenticationPrincipal User principal, @RequestParam UUID chatId) {
        service.joinToChat(principal.getId(), chatId);
    }
}
