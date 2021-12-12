package me.imatveev.thechat.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.Message;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTERED')")
@RequestMapping("/the-chat/v1/messages")
public class MessageController {

    @GetMapping("/chats/{chatId}")
    public List<Message> findAllByChatId(@PathVariable UUID chatId) {
        return null; //todo - fixit
    }

    @GetMapping("/chats/{chatId}/export")
    public MultipartFile exportMessages(@PathVariable UUID chatId) {
        return null; //todo - fixit
    }
}
