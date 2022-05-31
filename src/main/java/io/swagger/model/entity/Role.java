package io.swagger.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    EMPLOYEE,
    CUSTOMER;

    public String getAuthority(){
        return name();
    }
}
