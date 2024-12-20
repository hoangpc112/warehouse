package com.nttemoi.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "products")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String unit;
    private String description;
    private double weight;
    private double size;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "supplier_id", nullable = false)
    private Supplier supplier;
}
