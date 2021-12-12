package me.imatveev.thechat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends RuntimeException {
    private ChatNotFoundException(String message) {
        super(message);
    }

    public static ChatNotFoundException of(UUID id) {
        return new ChatNotFoundException("Can't find chat with id - " + id);
    }
}
