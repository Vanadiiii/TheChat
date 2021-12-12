package me.imatveev.thechat.web.model;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Jacksonized
public class MessageDto {
    UUID chatId;
    String text;
}
