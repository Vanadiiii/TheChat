package me.imatveev.thechat.exception;

import me.imatveev.thechat.domain.entity.ChatType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnavailableChatFoundException extends RuntimeException {
    private UnavailableChatFoundException(String message) {
        super(message);
    }

    public static UnavailableChatFoundException forDirect(UUID userId, UUID chatId) {
        return new UnavailableChatFoundException(
                "User with id - " + userId +
                        " can't join to chat with id - " + chatId +
                        ", cause it " + ChatType.DIRECT
        );
    }
}
