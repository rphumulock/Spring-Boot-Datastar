package com.example.demo.utilities;

public enum FragmentMergeType {
    MORPH_ELEMENT("morph_element"),
    INNER_ELEMENT("inner_element"),
    OUTER_ELEMENT("outer_element"),
    PREPEND_ELEMENT("prepend_element"),
    APPEND_ELEMENT("append_element"),
    BEFORE_ELEMENT("before_element"),
    AFTER_ELEMENT("after_element"),
    DELETE_ELEMENT("delete_element"),
    UPSERT_ATTRIBUTES("upsert_attributes");

    private final String value;

    // Constructor
    FragmentMergeType(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return this.value;
    }

    // Method to get enum from string value, useful for deserialization
    public static FragmentMergeType fromValue(String value) {
        for (FragmentMergeType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}
