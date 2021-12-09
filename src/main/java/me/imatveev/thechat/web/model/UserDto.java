package me.imatveev.thechat.web.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UserDto {
    String name;
    @NonNull
    String phone;
}
