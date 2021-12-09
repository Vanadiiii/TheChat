package me.imatveev.thechat.web.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ChatDto {
    String name;

    ChatDtoType type;
}


