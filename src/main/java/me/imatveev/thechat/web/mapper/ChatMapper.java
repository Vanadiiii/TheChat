package me.imatveev.thechat.web.mapper;


import me.imatveev.thechat.domain.entity.Chat;
import me.imatveev.thechat.web.model.ChatDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.function.Function;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ChatMapper extends Function<ChatDto, Chat> {
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Override
    Chat apply(ChatDto chatDto);
}
