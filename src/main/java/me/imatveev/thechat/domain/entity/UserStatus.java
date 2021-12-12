package me.imatveev.thechat.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserStatus implements GrantedAuthority {
    NEW,
    REGISTERED,
    DELETED,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
