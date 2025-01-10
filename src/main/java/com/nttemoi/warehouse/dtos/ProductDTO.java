package com.nttemoi.warehouse.dtos;


import com.nttemoi.warehouse.entities.ProductBom;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {
    private Long id;

    @NotNull (message = "Name is required")
    private String name;

    @NotNull (message = "Type is required")
    private String type;

    @NotNull (message = "Unit is required")
    private String unit;

    @Positive (message = "Weight must be positive")
    private double weight;

    @Positive (message = "Size must be positive")
    private double size;

    @NotNull (message = "Status is required")
    private String status;

    @NotNull (message = "Supplier is required")
    private Long supplierId;

    private List <ProductBom> productbomlist;
}