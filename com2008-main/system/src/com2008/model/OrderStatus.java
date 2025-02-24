package com2008.model;

    /**
 * The Products enum represents the products in the application.
 * Each Product has a corresponding name.
 */
public enum OrderStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    FULFILLED("Fulfilled");


    // Instance variable to hold the role name
    private final String orderStatus;

    /**
     * Constructor for the Products enum.
     *
     * @param productType The name associated with the role.
     */
    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Gets the name associated with the role.
     *
     * @return The role name.
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Converts a string to a UserRole enum constant.
     *
     * @param roleName The name of the role to convert.
     * @return The corresponding UserRole enum constant.
     * @throws IllegalArgumentException if no enum constant with the given roleName is found.
     */
    public static OrderStatus fromString(String orderStatus) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getOrderStatus().equalsIgnoreCase(orderStatus)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with roleName: " + orderStatus);
    }
}


