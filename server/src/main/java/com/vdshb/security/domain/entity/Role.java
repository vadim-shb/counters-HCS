package com.vdshb.security.domain.entity;

import com.vdshb.security.domain.enums.SecurityRole;

import javax.persistence.*;

@Entity
@Table(name = "security_role")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "security_role_id_seq")
    @SequenceGenerator(name = "security_role_id_seq", sequenceName = "security_role_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @ManyToOne
    @JoinColumn(name = "security_user_id")
    private SecurityUser securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SecurityRole getRole() {
        return role;
    }

    public void setRole(SecurityRole role) {
        this.role = role;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }
}
