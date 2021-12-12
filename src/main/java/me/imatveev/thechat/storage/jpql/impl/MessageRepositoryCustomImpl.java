package me.imatveev.thechat.storage.jpql.impl;

import lombok.RequiredArgsConstructor;
import me.imatveev.thechat.domain.entity.Message;
import me.imatveev.thechat.storage.jpql.MessageRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> findAllByChatId(UUID chatId, Pageable pageable) {
        String jpql = "select * from messages m " +
                "left join chats_messages cm on m.id = cm.message_id " +
                "left join chats c on c.id = cm.chat_id " +
                "where c.id = :chat_id " +
                "order by m.time ";

        return entityManager.createNativeQuery(jpql, Message.class)
                .setParameter("chat_id", chatId.toString())
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

}
