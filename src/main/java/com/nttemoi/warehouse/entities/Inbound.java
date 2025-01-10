package com.nttemoi.warehouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "inbounds")
public class Inbound {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    @Column (updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String status;

    @OneToMany (mappedBy = "inbound", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List <InboundDetails> inboundDetails;

    private long totalQuantity;
    private long totalDamaged;
    private String description;

    @ManyToOne
    @JoinColumn (name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;
}
