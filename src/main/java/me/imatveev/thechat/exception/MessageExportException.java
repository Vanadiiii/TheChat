package me.imatveev.thechat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class MessageExportException extends RuntimeException {
    private MessageExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public static MessageExportException of(Exception cause) {
        return new MessageExportException("Can't export messages", cause);
    }
}
