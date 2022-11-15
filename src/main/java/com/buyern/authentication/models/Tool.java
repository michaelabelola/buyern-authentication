package com.buyern.authentication.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "tools")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uid;
    @Column(nullable = false)
    private String name;
    private String about;

//    @ManyToMany(mappedBy = "tools")
//    @ToString.Exclude
//    private Set<CustomToken> customTokens = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tool tool = (Tool) o;
        return id != null && Objects.equals(id, tool.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
