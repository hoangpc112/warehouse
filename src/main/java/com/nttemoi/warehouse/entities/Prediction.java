package com.nttemoi.warehouse.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
    @JsonProperty ("Product_ID")
    private int productId;

    @JsonProperty ("Predict")
    private double predict;

    @JsonProperty ("avg_monthly")
    private double avgMonthly;

    private String productName;
}
