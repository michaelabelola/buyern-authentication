package com.buyern.authentication.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "user_auth")
public class UserAuth {
    @Id
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    private String password;
    @Column(nullable = false, unique = true)
    private String uid;
    private boolean verified = false;
    private boolean disabled = false;
    private boolean expired = false;
    private boolean credentialExpired = false;
    private boolean locked = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAuth userAuth = (UserAuth) o;
        return id != null && Objects.equals(id, userAuth.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
