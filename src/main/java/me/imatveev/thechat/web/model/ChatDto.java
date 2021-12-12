package me.imatveev.thechat.web.model;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
public class ChatDto {
    String name;
    ChatDtoType type;
}


