package com.nttemoi.warehouse.dtos;


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
    private String address;
    private long capacity;
    private String phoneNumber;

}
