package com.nttemoi.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "stock_balance")
public class StockBalance {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn (name = "warehouse_id")
    private Warehouse warehouse;

    @Column (name = "total_quantity")
    private Long totalQuantity;

    @Column (name = "status")
    private boolean status;

    @Column (name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column (name = "max_quantity")
    private Long maxQuantity;
}