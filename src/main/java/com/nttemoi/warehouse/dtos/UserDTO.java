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

    @NotEmpty(message = "Username không được để trống.")
    private String username;

    @Size(min = 6, message = "Mật khẩu phải có tổi thiếu 6 ký tự.")
    @NotEmpty(message = "Mật khẩu không thể để trống.")
    private String password;

    @NotEmpty(message = "Không thể để trống.")
    private String confirmPassword;

    private Set<Role> roles = new HashSet<>();

    public boolean validConfirmPassword() {
        return confirmPassword.equals(password);
    }
}