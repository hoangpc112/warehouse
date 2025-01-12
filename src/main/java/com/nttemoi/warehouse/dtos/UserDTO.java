package com.nttemoi.warehouse.dtos;

import com.nttemoi.warehouse.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    @NotEmpty (message = "Username is required")
    private String username;

    @Size (min = 6, message = "Password need to be at least 6 characters")
    @NotEmpty (message = "Password is required")
    private String password;

    @NotEmpty (message = "Confirm password is required")
    private String confirmPassword;

    private Set <Role> roles = new HashSet <>();

    public boolean validConfirmPassword () {
        return confirmPassword.equals(password);
    }
}