package me.imatveev.thechat.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Builder.Default
    @JsonIgnore
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    @JsonIgnore
    private List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(status);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return phone;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
