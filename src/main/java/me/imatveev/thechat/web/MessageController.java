package me.imatveev.thechat.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Message;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.MessageService;
import me.imatveev.thechat.web.model.MessageDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTERED')")
@SecurityRequirement(name = "bearerToken")
@RequestMapping("/the-chat/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/chats/{chatId}")
    @Operation(summary = "get page of messages from chat")
    public List<Message> findAllByChatId(@PathVariable UUID chatId,
                                         @RequestParam(defaultValue = "0", required = false) int page,
                                         @RequestParam(defaultValue = "30", required = false) int size) {
        return messageService.findInChat(chatId, page, size);
    }

    @GetMapping("/chats/{chatId}/export")
    @Operation(summary = "export all of messages from chat")
    public ResponseEntity<InputStreamResource> exportMessages(@PathVariable UUID chatId) throws IOException {
        Path path = messageService.exportAll(chatId);

        return ResponseEntity.ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s\"", path.toFile().getName()))
                .header("Cache-Control", "no-cache", "no-store", "must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .contentType(MediaType.parseMediaType("application/json"))
                .contentLength(path.toFile().length())
                .body(
                        new InputStreamResource(
                                Files.newInputStream(path)
                        )
                );
    }

    @PostMapping
    @Operation(summary = "send new message to chat")
    public void send(@AuthenticationPrincipal User principal, @RequestBody MessageDto messageDto) {
        messageService.send(
                principal.getId(),
                messageDto.getChatId(),
                messageDto.getText()
        );
    }
}
