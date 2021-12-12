package me.imatveev.thechat.web.model;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Value
@Jacksonized
public class StartGroupChatDto {
    @NonNull
    @NotEmpty
    List<UUID> companionIds;
    @NonNull
    @NotEmpty
    String chatName;
}
