package com.example.demo.utilities;

public enum QuerySelector {
    FRAGMENT_SELECTOR_SELF("self"),
    FRAGMENT_SELECTOR_USE_ID("");

    private final String value;

    // Constructor
    QuerySelector(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return this.value;
    }

    // Method to get enum from string value, useful for deserialization or matching
    public static QuerySelector fromValue(String value) {
        for (QuerySelector type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        // This handles the special case where the FRAGMENT_SELECTOR_USE_ID is meant to represent an empty string
        if (value.isEmpty()) {
            return FRAGMENT_SELECTOR_USE_ID;
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}
