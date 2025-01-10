package com.nttemoi.warehouse.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductBomDTO {
    private Long id;

    @NotNull (message = "Name is required")
    private String name;

    @Positive (message = "Quantity must be greater than 0")
    private long quantity;

    @NotNull (message = "Unit is required")
    private String unit;

    @NotNull (message = "Product is required")
    private Long productId;
}