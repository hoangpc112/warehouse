package com.nttemoi.warehouse.dtos;

import com.nttemoi.warehouse.entities.Role;
import com.nttemoi.warehouse.entities.Warehouse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDTO {
    private long id;

    private String phoneNumber;

    @NotEmpty
    private String address;

    @Email
    private String email;

    private Warehouse warehouse;

    private Set <Role> roles;

    @NotEmpty
    private String username;

    public boolean isPhoneNumberValid () {
        for (char ch : phoneNumber.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}