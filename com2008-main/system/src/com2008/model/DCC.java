package com2008.model;

    /**
 * The Products enum represents the products in the application.
 * Each Product has a corresponding name.
 */
public enum DCC {
    ANALOGUE("Analogue"),
    READY("Ready"),
    FITTED("Fitted"),
    SOUND("Sound");


    // Instance variable to hold the role name
    private final String dccType;

    /**
     * Constructor for the Products enum.
     *
     * @param productType The name associated with the role.
     */
    DCC(String dccType) {
        this.dccType = dccType;
    }

    /**
     * Gets the name associated with the role.
     *
     * @return The role name.
     */
    public String getDCCType() {
        return dccType;
    }

    /**
     * Converts a string to a UserRole enum constant.
     *
     * @param roleName The name of the role to convert.
     * @return The corresponding UserRole enum constant.
     * @throws IllegalArgumentException if no enum constant with the given roleName is found.
     */
    public static DCC fromString(String dccType) {
        for (DCC dcc : DCC.values()) {
            if (dcc.getDCCType().equalsIgnoreCase(dccType)) {
                return dcc;
            }
        }
        throw new IllegalArgumentException("No enum constant with roleName: " + dccType);
    }
}

