package com.buyern.authentication.models;

import com.buyern.authentication.enums.Permission;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "assigned_permissions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AssignedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
//    @OneToOne
//    @JoinColumn(name = "entity_id")
    private Long entityId;
//    @OneToOne
//    @JoinColumn(name = "user_id")
    private Long userId;
    @OneToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    private Permission permission;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeAssigned;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AssignedPermission that = (AssignedPermission) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
