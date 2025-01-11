package com.nttemoi.warehouse.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShowWarehouseDTO {

    @NotEmpty
    private Long warehouseId;


    private String warehouseAddress;

    private long warehouseCapacity;


    private String warehousePhoneNumber;

    private long warehouseNumProduct;

    private long warehouseNumUser;

    private boolean warehouseActive;


    public boolean isPhoneNumberValid() {
        for (char ch : warehousePhoneNumber.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

}
