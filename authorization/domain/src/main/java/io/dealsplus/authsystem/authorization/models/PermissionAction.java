package io.dealsplus.authsystem.authorization.models;

import lombok.Getter;

@Getter
public enum PermissionAction {
    READ("read"), WRITE("write"), DELETE("delete"), MANAGE("manage");

    private final String value;

    PermissionAction(String value) {
        this.value = value;
    }

    public static PermissionAction fromValue(String value) {
        for (PermissionAction action : PermissionAction.values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }
        return null;
    }

}
