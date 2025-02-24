package com2008.model;

    /**
 * The Products enum represents the products in the application.
 * Each Product has a corresponding name.
 */
public enum Gauges {
    OO("OO"),
    TT("TT"),
    N("N");

    // Instance variable to hold the role name
    private final String gaugeType;

    /**
     * Constructor for the Products enum.
     *
     * @param productType The name associated with the role.
     */
    Gauges(String gaugeType) {
        this.gaugeType = gaugeType;
    }

    /**
     * Gets the name associated with the role.
     *
     * @return The role name.
     */
    public String getGaugeType() {
        return gaugeType;
    }

    /**
     * Converts a string to a UserRole enum constant.
     *
     * @param roleName The name of the role to convert.
     * @return The corresponding UserRole enum constant.
     * @throws IllegalArgumentException if no enum constant with the given roleName is found.
     */
    public static Gauges fromString(String gaugeType) {
        for (Gauges gauge : Gauges.values()) {
            if (gauge.getGaugeType().equalsIgnoreCase(gaugeType)) {
                return gauge;
            }
        }
        throw new IllegalArgumentException("No enum constant with roleName: " + gaugeType);
    }
}
