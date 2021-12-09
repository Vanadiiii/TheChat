package me.imatveev.thechat.web.mapper;

import me.imatveev.thechat.domain.entity.User;
import me.imatveev.thechat.domain.entity.UserStatus;
import me.imatveev.thechat.web.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.function.Function;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = UserStatus.class)
public interface UserMapper extends Function<UserDto, User> {

    @Mapping(target = "status", constant = "NEW")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chats", ignore = true)
    @Override
    User apply(UserDto userDto);
}
