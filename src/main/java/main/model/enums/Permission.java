package main.model.enums;

public enum Permission {
    USER("user:write"),
    MODERATE("user:moderate");

    private final String permissions;

    Permission(String permission) {
        this.permissions = permission;
    }

    public String getPermissions() {
        return permissions;
    }
}
