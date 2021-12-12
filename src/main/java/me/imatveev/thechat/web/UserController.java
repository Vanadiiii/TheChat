package me.imatveev.thechat.web;

import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.service.UserService;
import me.imatveev.thechat.exception.UserNotFoundException;
import me.imatveev.thechat.web.mapper.UserMapper;
import me.imatveev.thechat.web.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/the-chat/v1/users")
@PreAuthorize("hasAnyAuthority('REGISTERED', 'ADMIN')")
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    @ApiResponse(description = "register new user", links = @Link(name = "register new user"))
    public User register(@RequestBody UserDto user) {
        return Optional.of(user)
                .map(mapper)
                .map(service::save)
                .orElseThrow();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(() -> UserNotFoundException.of(id));
    }

    @GetMapping("/chats/{chatId}")
    public List<User> findByChatId(@PathVariable UUID chatId) {
        return service.findByChatId(chatId);
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }
}
