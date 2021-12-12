package me.imatveev.thechat.web.model;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Jacksonized
public class StartGroupChatDto {
    List<UUID> companionIds;
    String chatName;
}
