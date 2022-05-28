package com.laptrinhjavaweb.enums;

public enum SpecialSearchParams {
    DISTRICT("district"),
    STAFF_ID("staffId"),
    BUILDING_TYPES("buildingTypes"),
    COST_RENT_FROM("costRentFrom"),
    COST_RENT_TO("costRentTo"),
    ARE_RENT_FROM("areaRentFrom"),
    ARE_RENT_TO("areaRentTo");

    private final String value;

    SpecialSearchParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
