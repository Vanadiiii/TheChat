package me.imatveev.thechat.domain.storage;

import me.imatveev.thechat.domain.entity.Message;

import java.util.Optional;
import java.util.UUID;

public interface MessageStorage {
    Optional<Message> findById(UUID id);
}
