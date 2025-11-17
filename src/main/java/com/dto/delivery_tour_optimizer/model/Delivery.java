package com.dto.delivery_tour_optimizer.model;

import com.dto.delivery_tour_optimizer.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double latitude;
    private double longitude;
    private double weight;    // en kg
    private double volume;    // en m³
    private String timeSlot;  // ex: "09:00-11:00"

    @Column(name = "delivery_order")
    private Integer deliveryOrder; // Ordre dans la tournée

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
}