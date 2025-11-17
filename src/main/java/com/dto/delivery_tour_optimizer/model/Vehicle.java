package com.dto.delivery_tour_optimizer.model;

import com.dto.delivery_tour_optimizer.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String licensePlate;
    private double maxWeight;    // en kg
    private double maxVolume;    // en m³
    private int maxDeliveries;   // nombre max de livraisons
}