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
@Table (name = "tutorials")
public class Tutorial {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (length = 128, nullable = false)
    private String title;

    @Column (length = 256)
    private String description;

    @Column (nullable = false)
    private int level;

    @Column
    private boolean published;
}