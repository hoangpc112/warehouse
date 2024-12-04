package com.nttemoi.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
}
