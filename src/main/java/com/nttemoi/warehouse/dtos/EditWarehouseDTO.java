package com.nttemoi.warehouse.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditWarehouseDTO {
    private long id;

    @NotBlank(message = "Warehouse address cannot be empty or blank")
    private String address;
    @NotEmpty
    private long capacity;
    @NotBlank(message = "Warehouse phone number cannot be empty or blank")
    private String phoneNumber;

    public boolean isPhoneNumberValid() {
        for (char ch : phoneNumber.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}
