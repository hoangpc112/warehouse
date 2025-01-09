package com.nttemoi.warehouse.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddWarehouseDTO {

    private Long warehouseId;
    private String warehouseAddress;
    private String warehousePhoneNumber;

    private long warehouseCapacity;
    private boolean warehouseActive;


}
