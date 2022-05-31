package io.swagger.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EMPLOYEE,
    ROLE_CUSTOMER;

    public String getAuthority(){
        return name();
    }
}
