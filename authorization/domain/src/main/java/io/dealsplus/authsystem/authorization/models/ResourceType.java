package io.dealsplus.authsystem.authorization.models;

import lombok.Getter;

@Getter
public enum ResourceType {
    ACCOUNT("account"),
    STRUCTURE("structure"),
    PERMISSION("permission"),
    ROLE("role");

    private final String value;

    ResourceType(String value) {
        this.value = value;
    }

    public static ResourceType fromValue(String value) {
        for (ResourceType action : ResourceType.values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }
        return null;
    }

}
