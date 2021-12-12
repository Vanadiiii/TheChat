package me.imatveev.thechat.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.web.model.StartGroupChatDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTERED')")
@RequestMapping("/the-chat/v1/chats")
@SecurityRequirement(name = "bearerToken")
public class ChatController {
    private final ChatService service;

    @GetMapping("/{id}")
    @Operation(summary = "find chat by it's id")
    public Chat findById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> ChatNotFoundException.of(id));
    }

    @GetMapping
    @Operation(summary = "find all user's chats")
    public List<Chat> findAllUsersChats(@AuthenticationPrincipal User principal) {
        return service.findByUserId(principal.getId());
    }

    @PostMapping("/direct-chat")
    @Operation(summary = "start new direct chat")
    public Chat startDirectChat(@AuthenticationPrincipal User principal,
                                @RequestParam UUID companionId) {
        return service.startDirectChat(
                principal.getId(),
                companionId
        );
    }

    @PostMapping("/group-chat")
    @Operation(summary = "start new group chat")
    public Chat startDirectChat(@AuthenticationPrincipal User principal,
                                @RequestBody StartGroupChatDto chatDto) {
        return service.startGroupChat(
                principal.getId(),
                chatDto.getCompanionIds(),
                chatDto.getChatName()
        );
    }

    @PutMapping("/join")
    @Operation(summary = "join to existed chat")
    public void joinToChat(@AuthenticationPrincipal User principal, @RequestParam UUID chatId) {
        service.joinToChat(principal.getId(), chatId);
    }
}
