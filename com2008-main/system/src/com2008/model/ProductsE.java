package com2008.model;

    /**
 * The Products enum represents the products in the application.
 * Each Product has a corresponding name.
 */
public enum ProductsE {
    ALL("ALL"),
    TRAINSET("Train Set"),
    TRACKPACK("Track Pack"),
    LOCOMOTIVE("Locomotive"),
    CONTROLLER("Controller"),
    ROLLINGSTOCK("Rolling Stock"),
    TRACK("Track");


    // Instance variable to hold the role name
    private final String productType;

    /**
     * Constructor for the Products enum.
     *
     * @param productType The name associated with the role.
     */
    ProductsE(String productType) {
        this.productType = productType;
    }

    /**
     * Gets the name associated with the role.
     *
     * @return The role name.
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Converts a string to a UserRole enum constant.
     *
     * @param roleName The name of the role to convert.
     * @return The corresponding UserRole enum constant.
     * @throws IllegalArgumentException if no enum constant with the given roleName is found.
     */
    public static ProductsE fromString(String productType) {
        for (ProductsE product : ProductsE.values()) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                return product;
            }
        }
        throw new IllegalArgumentException("No enum constant with roleName: " + productType);
    }
}

