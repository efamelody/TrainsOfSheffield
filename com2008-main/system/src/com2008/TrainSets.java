package com2008;

import com2008.model.Gauges;
import com2008.model.ProductsE;

public class TrainSets extends Products {

    private String eraCode;
    // Constructor to initialize a Book object with its attributes

    public TrainSets(String brandName, String productName, String productCode, int quantity, double retailPrice, 
                            Gauges gauge, ProductsE productType, String eraCode ) {
        super(brandName, productName, productCode, quantity, retailPrice, gauge, productType);
        this.eraCode = eraCode;
    }

    public String getEraCode(){
        return eraCode;
    }

    public void setEraCode(String eraCode){
        this.eraCode = eraCode;
    }
}
