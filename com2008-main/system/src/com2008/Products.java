package com2008;

import com2008.model.Gauges;
import com2008.model.ProductsE;

public class Products {
    private String brandName;
    private String productName;
    private String productCode;
    private int quantity;
    private double retailPrice;
    private Gauges gauge;
    private ProductsE productType;


    // Constructor to initialize a Book object with its attributes
    public Products(String brandName, String productName, String productCode, int quantity, double retailPrice, Gauges gauge, ProductsE productType ) {
        this.setBrandName(brandName);
        this.setProductName(productName);
        this.setProductCode(productCode);
        this.setQuantity(quantity);
        this.setRetailPrice(retailPrice);
        this.setGauge(gauge);
        this.setProductType(productType);
    }

    // Getter and setter methods for email with validation
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        if (isValidBrandName(brandName)) {
            this.brandName = brandName;
        } else {
            throw new IllegalArgumentException("Brand Name is not valid.");
        }
    }

    // Getter and setter methods for password hash with validation
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (isValidProductName(productName)) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Password Hash is not valid.");
        }
    }

        // Getter and setter methods for email with validation
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        if (isValidProductCode(productCode)) {
            this.productCode = productCode;
        } else {
            throw new IllegalArgumentException("Brand Name is not valid.");
        }
    }

    // Getter and setter methods for email with validation
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and setter methods for email with validation
    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

     // Getter and setter methods for email with validation
    public Gauges getGaugeType() {
        return gauge;
    }

    public void setGauge(Gauges gauge) {
        if (isValidGauge(gauge)) {
            this.gauge = gauge;
        } else {
            throw new IllegalArgumentException("Brand Name is not valid.");
        }
    }

     // Getter and setter methods for email with validation
    public ProductsE getProductType() {
        return productType;
    }

    public void setProductType(ProductsE productType) {
        if (isValidProductType(productType)) {
            this.productType = productType;
        } else {
            throw new IllegalArgumentException("Brand Name is not valid.");
        }
    }

    
    
    // Private validation methods for each attribute
    private boolean isValidBrandName(String brandName) {
        return brandName != null && brandName.length() <= 100;
    }

    private boolean isValidProductName(String productName) {
        return productName != null && productName.length() <= 255;
    }

    private boolean isValidProductCode(String productCode) {
        return productCode != null && productCode.length() <= 255;
    }

    private boolean isValidGauge(Gauges gauge) {
        return (gauge.equals(Gauges.TT) || gauge.equals(Gauges.OO) || gauge.equals(Gauges.N));
    }

    private boolean isValidProductType(ProductsE productType) {
        return (productType.equals(ProductsE.TRAINSET) || productType.equals(ProductsE.TRACKPACK) || productType.equals(ProductsE.LOCOMOTIVE)
                || productType.equals(ProductsE.CONTROLLER) || productType.equals(ProductsE.ROLLINGSTOCK) || productType.equals(ProductsE.TRACK));
    }



    //         // Getter and setter methods for email with validation
    // public String getProductCode() {
    //     return productCode;
    // }

    // public void setProductCode(String productCode) {
    //     if (isValidProductCode(productCode)) {
    //         this.productCode = productCode;
    //     } else {
    //         throw new IllegalArgumentException("Brand Name is not valid.");
    //     }
    // }

    // // Private validation methods for each attribute
    // private boolean isValidBrandName(String brandName) {
    //     return brandName != null && brandName.length() <= 100;
    // }

    //         // Getter and setter methods for email with validation
    // public String getProductCode() {
    //     return productCode;
    // }

    // public void setProductCode(String productCode) {
    //     if (isValidProductCode(productCode)) {
    //         this.productCode = productCode;
    //     } else {
    //         throw new IllegalArgumentException("Brand Name is not valid.");
    //     }
    // }

    // // Private validation methods for each attribute
    // private boolean isValidBrandName(String brandName) {
    //     return brandName != null && brandName.length() <= 100;
    // }

    













    @Override
    public String toString() {
        return "{ " +
                " brandName ='" + getBrandName() + "'" +
                ", productName='" + getProductName() + "'" +
                " }";
    }
}

    

    
