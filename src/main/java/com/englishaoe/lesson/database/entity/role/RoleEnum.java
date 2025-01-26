package com.englishaoe.lesson.database.entity.role;

public enum RoleEnum {
    ADMIN("admin"),
    PARTNER("partner");
    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
