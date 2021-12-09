package me.imatveev.thechat;

import lombok.RequiredArgsConstructor;
import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.domain.entity.ChatType;
import me.imatveev.thechat.domain.entity.Message;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.entity.UserStatus;
import me.imatveev.thechat.storage.ChatRepository;
import me.imatveev.thechat.storage.MessageRepository;
import me.imatveev.thechat.storage.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class TheChatApplication {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public static void main(String[] args) {
        SpringApplication.run(TheChatApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            User ivan = User.builder()
                    .name("Ivan")
                    .phone("88005553535")
                    .status(UserStatus.REGISTERED)
                    .build();
            User anna = User.builder()
                    .name("Anna")
                    .phone("88005553546")
                    .status(UserStatus.REGISTERED)
                    .build();
            userRepository.saveAndFlush(ivan);
            userRepository.saveAndFlush(anna);

            Chat chat = Chat.builder()
                    .name("func")
                    .type(ChatType.GROUP)
                    .users(List.of(ivan, anna))
                    .build();
            chatRepository.saveAndFlush(chat);

            Message message = Message.builder()
                    .user(ivan)
                    .value("Hello, kitty!)")
                    .build();
            messageRepository.saveAndFlush(message);

            chat.addMessage(message);
            chatRepository.saveAndFlush(chat);
        };
    }
}
