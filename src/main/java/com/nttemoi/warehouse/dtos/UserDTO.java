package com.nttemoi.warehouse.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    @NotEmpty (message = "Username không được để trống.")
    private String username;

    @Size (min = 6, message = "Mật khẩu phải có tổi thiếu 6 ký tự.")
    @NotEmpty (message = "Mật khẩu không thể để trống.")
    private String password;

    @NotEmpty (message = "Không thể để trống.")
    private String confirmPassword;
}