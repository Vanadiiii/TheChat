package me.imatveev.thechat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.configuration.properties.MessageProperties;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.entity.Message;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.ChatService;
import me.imatveev.thechat.domain.service.MessageService;
import me.imatveev.thechat.domain.service.UserService;
import me.imatveev.thechat.domain.storage.MessageStorage;
import me.imatveev.thechat.exception.ChatNotFoundException;
import me.imatveev.thechat.exception.MessageExportException;
import me.imatveev.thechat.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageStorage messageStorage;
    private final ChatService chatService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final MessageProperties messageProperties;

    @Override
    public List<Message> findInChat(UUID chatId, int page, int size) {
        return messageStorage.findAllByChatId(chatId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public Path exportAll(UUID chatId) {
        Path tempFile;

        try {
            tempFile = Files.createTempFile(UUID.randomUUID().toString(), ".json");

            Files.write(tempFile, "[".getBytes(StandardCharsets.UTF_8));

            int page = 0;

            while (true) {
                List<Message> messageBatch = findInChat(chatId, page, messageProperties.getBatchSize());

                if (messageBatch.isEmpty()) {
                    break;
                }

                if (page != 0) {
                    Files.write(tempFile, ",".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }

                Files.write(
                        tempFile,
                        messageBatch.stream()
                                .map(message -> {
                                    try {
                                        return objectMapper.writeValueAsString(message);
                                    } catch (JsonProcessingException e) {
                                        throw MessageExportException.of(e);
                                    }
                                })
                                .collect(Collectors.joining(","))
                                .getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND
                );

                page++;
            }

            Files.write(tempFile, "]".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw MessageExportException.of(e);
        }

        return tempFile;
    }

    @Override
    @Transactional
    public void send(UUID userId, UUID chatId, String text) {
        Chat chat = chatService.findById(chatId)
                .orElseThrow(() -> ChatNotFoundException.of(chatId));

        User user = userService.findById(userId)
                .orElseThrow(() -> UserNotFoundException.of(userId));

        Message message = Message.builder()
                .value(text)
                .user(user)
                .build();
        messageStorage.save(message);

        chat.addMessage(message);
    }
}
