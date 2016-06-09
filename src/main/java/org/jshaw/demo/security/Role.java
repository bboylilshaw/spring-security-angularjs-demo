package org.jshaw.demo.security;

public enum Role {
    ADMIN,
    USER;

    public String get() {
        return "ROLE_" + this.toString().toUpperCase();
    }
}
