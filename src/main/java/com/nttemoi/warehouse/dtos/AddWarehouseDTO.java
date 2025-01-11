package com.nttemoi.warehouse.dtos;


import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Warehouse address cannot be empty or blank")
    private String warehouseAddress;
    @NotBlank(message = "Warehouse phone number cannot be empty or blank")
    private String warehousePhoneNumber;


    private long warehouseCapacity;

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
