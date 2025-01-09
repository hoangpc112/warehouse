package com.nttemoi.warehouse.dtos;

import com.nttemoi.warehouse.entities.Role;
import com.nttemoi.warehouse.entities.Warehouse;
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
    private String address;
    private String email;
    private Warehouse warehouse;
    private Set<Role> roles;
    private String username;
}
