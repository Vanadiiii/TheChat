package me.imatveev.thechat.storage;

import me.imatveev.thechat.domain.entity.Message;
import me.imatveev.thechat.domain.storage.MessageStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID>, MessageStorage {
}
