package com.nttemoi.warehouse.dtos;

import com.nttemoi.warehouse.entities.Product;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SupplierDTO {
    private Long id;

    @NotNull (message = "Name is required")
    private String name;

    @NotNull (message = "Address is required")
    private String address;

    @NotNull (message = "Phone is required")
    private String phone;

    @NotNull (message = "Email is required")
    @Email (message = "Invalid email format")
    private String email;

    @NotNull (message = "Status is required")
    private String status;

    private List <Product> products;
}