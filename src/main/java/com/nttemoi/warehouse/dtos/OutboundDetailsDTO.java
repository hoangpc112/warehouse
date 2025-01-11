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

public class OutboundDetailsDTO {
    private Long id;
    private Long outboundId;

    @NotNull (message = "Product is required")
    private Long productId;

    @NotNull (message = "Warehouse is required")
    private Long warehouseId;

    @NotNull (message = "Quantity is required")
    @Positive (message = "Quantity must be greater than 0")
    private Long quantity;

    @NotNull (message = "Status is required")
    private String status;
}
