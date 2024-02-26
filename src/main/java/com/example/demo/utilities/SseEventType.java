package com.example.demo.utilities;

public enum SseEventType {
    SSE_EVENT_TYPE_FRAGMENT("datastar-fragment"),
    SSE_EVENT_TYPE_REDIRECT("datastar-redirect"),
    SSE_EVENT_TYPE_ERROR("datastar-error");

    private final String value;

    // Constructor
    SseEventType(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }
}
