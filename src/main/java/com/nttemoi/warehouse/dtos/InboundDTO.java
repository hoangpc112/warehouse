package com.nttemoi.warehouse.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InboundDTO {
    private Long id;

    @NotNull (message = "Supplier is required")
    private Long supplierId;

    @NotNull (message = "User is required")
    private Long userId;

    @NotNull (message = "Status is required")
    private String status;
}
